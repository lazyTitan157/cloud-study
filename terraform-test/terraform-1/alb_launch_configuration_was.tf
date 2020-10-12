resource "aws_launch_configuration" "was" {
  name_prefix = "user06-autoscaling-was-"

  image_id = "ami-0241c2a53b5e99dc1"#"${var.amazon_linux}"
  instance_type = "t2.micro"
  key_name = "${var.user06_keyname}"
  security_groups = [
    "${aws_security_group.alb_2.id}",
    "${aws_security_group.bastion.id}",
    "${aws_default_security_group.user06_default.id}",
  ]
  associate_public_ip_address = true
  
  lifecycle {
    create_before_destroy = true
  }
}