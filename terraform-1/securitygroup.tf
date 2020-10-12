resource "aws_default_security_group" "user06_default" {
    vpc_id = "${aws_vpc.user06.id}"
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
        Name = "user06-default"
    }
}

resource "aws_security_group" "bastion" {
    name = "bastion"
    description = "open ssh port for bastion"
    vpc_id = "${aws_vpc.user06.id}"
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
        Name = "user06-bastion"
    }
}

resource "aws_security_group" "alb_1" {
    name = "alb-1"
    description = "open HTTP port for ALB"
    vpc_id = "${aws_vpc.user06.id}"
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
        Name = "user06-ALB-1"
    }
}


resource "aws_security_group" "alb_2" {
    name = "alb-2"
    description = "open WAS port for ALB"
    vpc_id = "${aws_vpc.user06.id}"
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
        Name = "user06-ALB-2"
    }
}