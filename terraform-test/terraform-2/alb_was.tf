resource "aws_alb" "alb2_2" {
    name = "user106-alb-2"
    internal = false
    security_groups = ["${aws_security_group.alb2_2.id}"]
    subnets = [
        "${aws_subnet.public2_1a.id}",
        "${aws_subnet.public2_1c.id}"
    ]
    access_logs {
        bucket = "${aws_s3_bucket.alb.id}"
        prefix = "was-alb"
        enabled = true
    }
    tags = {
        Name = "user106-ALB-2"
    }
    lifecycle { create_before_destroy = true }
}

#alb target group
resource "aws_alb_target_group" "was" {
    name = "user106-was-target-group"
    port = 8080
    protocol = "HTTP"
    vpc_id = "${aws_vpc.user106.id}"
    health_check {
        interval = 30
        path = "/ping"
        healthy_threshold = 3
        unhealthy_threshold = 3
    }
    tags = { Name = "user106-Backend Target Group" }
}
resource "aws_alb_target_group_attachment" "was" {
    target_group_arn = "${aws_alb_target_group.was.arn}"
    target_id = "${aws_instance.bastion2_2a.id}"
    port = 8080
}
resource "aws_alb_target_group_attachment" "was_2c" {
    target_group_arn = "${aws_alb_target_group.was.arn}"
    target_id = "${aws_instance.bastion2_2c.id}"
    port = 8080
}

resource "aws_alb_listener" "httpd" {
    load_balancer_arn = "${aws_alb.alb2_2.arn}"
    port = "8080"
    protocol = "HTTP"
    default_action {
        target_group_arn = "${aws_alb_target_group.was.arn}"
        type = "forward"
    }
}