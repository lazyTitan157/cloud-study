resource "aws_autoscaling_policy" "was_policy_down" {
  name = "was_policy_down"
  scaling_adjustment = -1
  adjustment_type = "ChangeInCapacity"
  cooldown = 300
  autoscaling_group_name = "${aws_autoscaling_group.was.name}"
}

resource "aws_cloudwatch_metric_alarm" "was_cpu_alarm_down" {
  alarm_name = "was_cpu_alarm_down"
  comparison_operator = "LessThanOrEqualToThreshold"
  evaluation_periods = "2"
  metric_name = "CPUUtilization"
  namespace = "AWS/EC2"
  period = "120"
  statistic = "Average"
  threshold = "10"

  #dimensions {
  #  AutoScalingGroupName = "${aws_autoscaling_group.web.name}"
  #:}

  alarm_description = "This metric monitor EC2 instance CPU utilization"
  alarm_actions = ["${aws_autoscaling_policy.was_policy_down.arn}"]
}