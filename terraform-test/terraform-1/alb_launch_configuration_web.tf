resource "aws_launch_configuration" "web" {
  name_prefix = "user06-autoscaling-web-"

  image_id = "${var.amazon_linux}"
  instance_type = "t2.micro"
  key_name = "${var.user06_keyname}"
    user_data = <<USER_DATA
#!/bin/bash
sudo su -
yum update -y
yum -y install httpd
echo "<html>User06 Region Ohio or London<img src=\"http://d2827cs1l6uk7w.cloudfront.net/images/image.jpg\"/></html>" > /var/www/html/index.html
service httpd start
    USER_DATA

  security_groups = [
    "${aws_security_group.alb_1.id}",
    "${aws_security_group.bastion.id}",
    "${aws_default_security_group.user06_default.id}",
  ]
  associate_public_ip_address = true

  lifecycle {
    create_before_destroy = true
  }
}