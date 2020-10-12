resource "aws_instance" "bastion2_1a" {
    ami = "${var.amazon_linux}"
    availability_zone = "eu-west-2a"
    instance_type = "t2.micro"
    key_name = "${var.user106_keyname}"
    
    
    user_data = <<USER_DATA
#!/bin/bash
sudo su -
yum update -y
yum -y install httpd
echo "<html>User06 Region Ohio or London<img src=\"http://d3svxwphh08r40.cloudfront.net/image.jpg\"/></html>" > /var/www/html/index.html
service httpd start
    USER_DATA
    vpc_security_group_ids = [
        "${aws_security_group.alb2_1.id}",
        "${aws_security_group.bastion2.id}",
        "${aws_default_security_group.user106_default.id}",
    ]
    subnet_id = "${aws_subnet.public2_1a.id}"
    associate_public_ip_address = true
    tags = {
        Name = "user106-bastion-1a"
    }
}

resource "aws_instance" "bastion2_1c" {
    ami = "${var.amazon_linux}"
    availability_zone = "eu-west-2c"
    instance_type = "t2.micro"
    key_name = "${var.user106_keyname}"

    
    user_data = <<USER_DATA
#!/bin/bash
sudo su -
yum update -y
yum -y install httpd
echo "<html>User06 Region Ohio or London<img src=\"http://d3svxwphh08r40.cloudfront.net/image.jpg\"/></html>" > /var/www/html/index.html
service httpd start
    USER_DATA
    vpc_security_group_ids = [
        "${aws_security_group.alb2_1.id}",
        "${aws_security_group.bastion2.id}",
        "${aws_default_security_group.user106_default.id}",
    ]
    subnet_id = "${aws_subnet.public2_1c.id}"
    associate_public_ip_address = true
    tags = {
        Name = "user106-bastion-1c"
    }
}

resource "aws_instance" "bastion2_2a" {
    ami = "ami-0294533f8c3a11f68" #"${var.amazon_linux}"
    availability_zone = "eu-west-2a"
    instance_type = "t2.micro"
    key_name = "${var.user106_keyname}"
    

    user_data = <<USER_DATA
#!/bin/bash
sudo su -
service httpd start
    USER_DATA
    
    vpc_security_group_ids = [
        "${aws_security_group.alb2_2.id}",
        "${aws_security_group.bastion2.id}",
        "${aws_default_security_group.user106_default.id}",
    ]
    subnet_id = "${aws_subnet.public2_1a.id}"
    associate_public_ip_address = true
    tags = {
        Name = "user106-bastion-2a"
    }
}

resource "aws_instance" "bastion2_2c" {
    ami = "ami-0294533f8c3a11f68" #"${var.amazon_linux}"
    availability_zone = "eu-west-2c"
    instance_type = "t2.micro"
    key_name = "${var.user106_keyname}"
   
    user_data = <<USER_DATA
#!/bin/bash
sudo su -
service httpd start
    USER_DATA
    
    vpc_security_group_ids = [
        "${aws_security_group.alb2_2.id}",
        "${aws_security_group.bastion2.id}",
        "${aws_default_security_group.user106_default.id}",
    ]
    subnet_id = "${aws_subnet.public2_1c.id}"
    associate_public_ip_address = true
    tags = {
        Name = "user106-bastion-2c"
    }
}