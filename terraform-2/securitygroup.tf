resource "aws_default_security_group" "user106_default" {
    vpc_id = "${aws_vpc.user106.id}"
    ingress {
        protocol = -1
        self = true
        from_port = 0
        to_port = 0
    }
    egress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
    tags = {
        Name = "user106-default"
    }
}

resource "aws_security_group" "bastion2" {
    name = "bastion2"
    description = "open ssh port for bastion2"
    vpc_id = "${aws_vpc.user106.id}"
    ingress {
        from_port = 22
        to_port = 22
        protocol = "tcp"
        cidr_blocks = ["211.226.71.113/32"]
    }
    egress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
    tags = {
        Name = "user106-bastion"
    }
}

resource "aws_security_group" "alb2_1" {
    name = "alb2_1"
    description = "open HTTP port for ALB2"
    vpc_id = "${aws_vpc.user106.id}"
    ingress {
        from_port = 80
        to_port = 80
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    egress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
    tags = {
        Name = "user106-ALB"
    }
}


resource "aws_security_group" "alb2_2" {
    name = "alb2_2"
    description = "open WAS port for ALB2"
    vpc_id = "${aws_vpc.user106.id}"
    ingress {
        from_port = 8080
        to_port = 8080
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    egress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
    tags = {
        Name = "user106-ALB"
    }
}