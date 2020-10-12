resource "aws_default_network_acl" "user06_default" {
    default_network_acl_id = "${aws_vpc.user06.default_network_acl_id}"
    ingress {
        protocol = -1
        rule_no = 100
        action = "allow"
        cidr_block = "0.0.0.0/0"
        from_port = 0
        to_port = 0
    }
    egress {
        protocol = -1
        rule_no = 100
        action = "allow"
        cidr_block = "0.0.0.0/0"
        from_port = 0
        to_port = 0
    }
    subnet_ids = [ "${aws_subnet.public_1a.id}", ]
    tags = {
        Name = "user06-default"
    }
}
