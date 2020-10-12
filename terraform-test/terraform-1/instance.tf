# Subnet A & AZ1
resource "aws_instance" "bastion_1a" {
    ami = "${var.amazon_linux}"
    availability_zone = "us-east-2a"
    instance_type = "t2.micro"
    key_name = "${var.user06_keyname}"
    user_data = <<USER_DATA
#!/bin/bash
sudo su -
yum update -y
yum -y install httpd
echo "<html>User06 Region Ohio or London<img src=\"http://d3svxwphh08r40.cloudfront.net/image.jpg\"/></html>" > /var/www/html/index.html
service httpd start
    USER_DATA

    vpc_security_group_ids = [
        "${aws_security_group.alb_1.id}",
        "${aws_security_group.bastion.id}",
        "${aws_default_security_group.user06_default.id}",
    ]
    subnet_id = "${aws_subnet.public_1a.id}"
    associate_public_ip_address = true
    tags = {
        Name = "user06-bastion-1a"
    }
}

# Subnet B & AZ2
resource "aws_instance" "bastion_1c" {
    ami = "${var.amazon_linux}"
    availability_zone = "us-east-2c"
    instance_type = "t2.micro"
    key_name = "${var.user06_keyname}"
    user_data = <<USER_DATA
#!/bin/bash
sudo su -
yum update -y
yum -y install httpd
echo "<html>User06 Region Ohio or London<img src=\"http://d3svxwphh08r40.cloudfront.net/image.jpg\"/></html>" > /var/www/html/index.html
service httpd start
    USER_DATA

    vpc_security_group_ids = [
        "${aws_security_group.alb_1.id}",
        "${aws_security_group.bastion.id}",
        "${aws_default_security_group.user06_default.id}",
    ]
    subnet_id = "${aws_subnet.public_1c.id}"
    associate_public_ip_address = true
    tags = {
        Name = "user06-bastion-1c"
    }
}

# Subnet A & AZ 1
resource "aws_instance" "bastion_2a" {
    ami = "ami-0241c2a53b5e99dc1"#"${var.amazon_linux}"
    availability_zone = "us-east-2a"
    instance_type = "t2.micro"
    key_name = "${var.user06_keyname}"

    user_data = <<USER_DATA
#!/bin/bash
sudo su -
service httpd start
    USER_DATA
    vpc_security_group_ids = [
        "${aws_security_group.alb_2.id}",
        "${aws_security_group.bastion.id}",
        "${aws_default_security_group.user06_default.id}",
    ]
    subnet_id = "${aws_subnet.public_1a.id}"
    associate_public_ip_address = true
    tags = {
        Name = "user06-bastion-2a"
    }
}

# Subnet B & AZ 2
resource "aws_instance" "bastion_2c" {
    ami = "ami-0241c2a53b5e99dc1" #"${var.amazon_linux}"
    availability_zone = "us-east-2c"
    instance_type = "t2.micro"
    key_name = "${var.user06_keyname}"

    user_data = <<USER_DATA
#!/bin/bash
sudo su -
service httpd start
    USER_DATA

    vpc_security_group_ids = [
        "${aws_security_group.alb_2.id}",
        "${aws_security_group.bastion.id}",
        "${aws_default_security_group.user06_default.id}",
    ]
    subnet_id = "${aws_subnet.public_1c.id}"
    associate_public_ip_address = true
    tags = {
        Name = "user06-bastion-2c"
    }
}
