resource "aws_launch_configuration" "was" {
  name_prefix = "user106-autoscaling-was-"

  image_id = "ami-0294533f8c3a11f68" #"${var.amazon_linux}"
  instance_type = "t2.micro"
  key_name = "${var.user106_keyname}"
  
  
  user_data = <<USER_DATA
#!/bin/bash
sudo su -
service httpd start
  USER_DATA
    
  security_groups = [
    "${aws_security_group.alb2_2.id}",
    "${aws_security_group.bastion2.id}",
    "${aws_default_security_group.user106_default.id}",
  ]
  associate_public_ip_address = true
  
  lifecycle {
    create_before_destroy = true
  }
}