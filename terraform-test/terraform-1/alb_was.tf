resource "aws_alb" "alb_2" {
    name = "user06-alb-2"
    internal = false
    security_groups = ["${aws_security_group.alb_2.id}"]
    subnets = [
        "${aws_subnet.public_1a.id}",
        "${aws_subnet.public_1c.id}"
    ]
    access_logs {
        bucket = "${aws_s3_bucket.alb.id}"
        prefix = "was-alb"
        enabled = true
    }
    tags = {
        Name = "user06-ALB-2"
    }
    lifecycle { create_before_destroy = true }
}

#alb target group
resource "aws_alb_target_group" "was" {
    name = "user06-was-target-group"
    port = 8080
    protocol = "HTTP"
    vpc_id = "${aws_vpc.user06.id}"
    health_check {
        interval = 30
        path = "/ping"
        healthy_threshold = 3
        unhealthy_threshold = 3
    }
    tags = { Name = "user06-Backend Target Group" }
}
resource "aws_alb_target_group_attachment" "was" {
    target_group_arn = "${aws_alb_target_group.was.arn}"
    target_id = "${aws_instance.bastion_2a.id}"
    port = 8080
}
resource "aws_alb_target_group_attachment" "was_2c" {
    target_group_arn = "${aws_alb_target_group.was.arn}"
    target_id = "${aws_instance.bastion_2c.id}"
    port = 8080
}

resource "aws_alb_listener" "httpd" {
    load_balancer_arn = "${aws_alb.alb_2.arn}"
    port = "8080"
    protocol = "HTTP"
    default_action {
        target_group_arn = "${aws_alb_target_group.was.arn}"
        type = "forward"
    }
}