resource "aws_vpc" "user06" {
    cidr_block = "6.0.0.0/16"
    enable_dns_hostnames = true
    enable_dns_support = true
    instance_tenancy = "default"
    tags = {
        Name = "user06-vpc-test"
    }
}

resource "aws_subnet" "public_1a" {
    vpc_id = "${aws_vpc.user06.id}"
    availability_zone = "us-east-2a"
    cidr_block = "6.0.1.0/24"
    tags = {
        Name = "user06-public-1a"
    }
}

resource "aws_subnet" "public_1c" {
    vpc_id = "${aws_vpc.user06.id}"
    availability_zone = "us-east-2c"
    cidr_block = "6.0.2.0/24"
    tags = {
        Name = "user06-public-1c"
    }
}

resource "aws_internet_gateway" "user06" {
    vpc_id = "${aws_vpc.user06.id}"
    tags = {
        Name = "user06"
    }
}

# user06_public route table
resource "aws_route_table" "user06_public" {
    vpc_id = "${aws_vpc.user06.id}"
    
    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = "${aws_internet_gateway.user06.id}"
    }
    tags = {
        Name = "user06-public-rt"
    }
}

resource "aws_route_table_association" "user06_public_1a" {
    subnet_id = "${aws_subnet.public_1a.id}"
    route_table_id = "${aws_route_table.user06_public.id}"
}
resource "aws_route_table_association" "user06_public_1c" {
    subnet_id = "${aws_subnet.public_1c.id}"
    route_table_id = "${aws_route_table.user06_public.id}"
}

resource "aws_nat_gateway" "user06_1a" {
    allocation_id = "${aws_eip.nat_user06_1a.id}"
    subnet_id = "${aws_subnet.public_1a.id}"
}
