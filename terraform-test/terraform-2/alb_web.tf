resource "aws_alb" "alb2_1" {
    name = "user106-alb-1"
    internal = false
    security_groups = ["${aws_security_group.alb2_1.id}"]
    subnets = [
        "${aws_subnet.public2_1a.id}",
        "${aws_subnet.public2_1c.id}"
    ]
    access_logs {
        bucket = "${aws_s3_bucket.alb.id}"
        prefix = "web-alb"
        enabled = true
    }
    tags = {
        Name = "user106-ALB-1"
    }
    lifecycle { create_before_destroy = true }
}

#alb target group
resource "aws_alb_target_group" "web" {
    name = "user106-web-target-group"
    port = 80
    protocol = "HTTP"
    vpc_id = "${aws_vpc.user106.id}"
    health_check {
        interval = 30
        path = "/ping"
        healthy_threshold = 3
        unhealthy_threshold = 3
    }
    tags = { Name = "user106-web Target Group" }
}
resource "aws_alb_target_group_attachment" "web" {
    target_group_arn = "${aws_alb_target_group.web.arn}"
    target_id = "${aws_instance.bastion2_1a.id}"
    port = 80
}
resource "aws_alb_target_group_attachment" "web_1c" {
    target_group_arn = "${aws_alb_target_group.web.arn}"
    target_id = "${aws_instance.bastion2_1c.id}"
    port = 80
}

resource "aws_alb_listener" "http" {
    load_balancer_arn = "${aws_alb.alb2_1.arn}"
    port = "80"
    protocol = "HTTP"
    default_action {
        target_group_arn = "${aws_alb_target_group.web.arn}"
        type = "forward"
    }
}