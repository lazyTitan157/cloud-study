resource "aws_vpc" "user106" {
    cidr_block = "106.0.0.0/16"
    enable_dns_hostnames = true
    enable_dns_support = true
    instance_tenancy = "default"
    tags = {
        Name = "user106-vpc"
    }
}

resource "aws_subnet" "public2_1a" {
    vpc_id = "${aws_vpc.user106.id}"
    availability_zone = "eu-west-2a"
    cidr_block = "106.0.1.0/24"
    tags = {
        Name = "user106-public-1a"
    }
}

resource "aws_subnet" "public2_1c" {
    vpc_id = "${aws_vpc.user106.id}"
    availability_zone = "eu-west-2c"
    cidr_block = "106.0.2.0/24"
    tags = {
        Name = "user106-public-1c"
    }
}

resource "aws_internet_gateway" "user106" {
    vpc_id = "${aws_vpc.user106.id}"
    tags = {
        Name = "user106-gw"
    }
}

# user06_public route table
resource "aws_route_table" "user106_public" {
    vpc_id = "${aws_vpc.user106.id}"
    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = "${aws_internet_gateway.user106.id}"
    }
    tags = {
        Name = "user106-public-rt"
    }
}

resource "aws_route_table_association" "user106_public_1a" {
    subnet_id = "${aws_subnet.public2_1a.id}"
    route_table_id = "${aws_route_table.user106_public.id}"
}
resource "aws_route_table_association" "user106_public_1c" {
    subnet_id = "${aws_subnet.public2_1c.id}"
    route_table_id = "${aws_route_table.user106_public.id}"
}

resource "aws_nat_gateway" "user106_1a" {
    allocation_id = "${aws_eip.nat_user106_1a.id}"
    subnet_id = "${aws_subnet.public2_1a.id}"
}