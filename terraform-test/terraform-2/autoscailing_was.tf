resource "aws_autoscaling_group" "was" {
  name = "${aws_launch_configuration.was.name}-asg"

  min_size             = 1
  desired_capacity     = 2
  max_size             = 3

  health_check_type    = "ELB"
  #load_balancers= ["${aws_alb.alb.id}" ] #this elb means classic lb
  #alb_target_group_arn = "${aws_alb_target_group.was.arn}"
  target_group_arns   = ["${aws_alb_target_group.was.arn}"]
  #alb = "${aws_alb.alb.id}"
  
  launch_configuration = "${aws_launch_configuration.was.name}"
  ####  availability_zones = ["ap-southeast-1a", "ap-southeast-1b"]  아ㅐㄹ vpc_zone_identifier 와 중복

  enabled_metrics = [
    "GroupMinSize",
    "GroupMaxSize",
    "GroupDesiredCapacity",
    "GroupInServiceInstances",
    "GroupTotalInstances"
  ]

  metrics_granularity="1Minute"

  vpc_zone_identifier  = [
    "${aws_subnet.public2_1a.id}",
    "${aws_subnet.public2_1c.id}"
  ]

  # Required to redeploy without an outage.
  lifecycle {
    create_before_destroy = true
  }

  tag {
    key                 = "Name"
    value               = "user106-was-autoscaling"
    propagate_at_launch = true
  }
}
