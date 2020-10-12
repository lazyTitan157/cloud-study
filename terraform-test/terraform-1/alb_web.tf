resource "aws_alb" "alb_1" {
    name = "user06-alb-1"
    internal = false
    security_groups = ["${aws_security_group.alb_1.id}"]
    subnets = [
        "${aws_subnet.public_1a.id}",
        "${aws_subnet.public_1c.id}"
    ]
    access_logs {
        bucket = "${aws_s3_bucket.alb.id}"
        prefix = "frontend-alb"
        enabled = true
    }
    tags = {
        Name = "user06-ALB-1"
    }
    lifecycle { create_before_destroy = true }
}

#alb target group
resource "aws_alb_target_group" "frontend" {
    name = "user06-frontend-target-group"
    port = 80
    protocol = "HTTP"
    vpc_id = "${aws_vpc.user06.id}"
    health_check {
        interval = 30
        path = "/ping"
        healthy_threshold = 3
        unhealthy_threshold = 3
    }
    tags = { Name = "user06-Frontend Target Group" }
}
resource "aws_alb_target_group_attachment" "frontend" {
    target_group_arn = "${aws_alb_target_group.frontend.arn}"
    target_id = "${aws_instance.bastion_1a.id}"
    port = 80
}
resource "aws_alb_target_group_attachment" "frontend_1c" {
    target_group_arn = "${aws_alb_target_group.frontend.arn}"
    target_id = "${aws_instance.bastion_1c.id}"
    port = 80
}

resource "aws_alb_listener" "http" {
    load_balancer_arn = "${aws_alb.alb_1.arn}"
    port = "80"
    protocol = "HTTP"
    default_action {
        target_group_arn = "${aws_alb_target_group.frontend.arn}"
        type = "forward"
    }
}