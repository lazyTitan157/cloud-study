
resource "aws_autoscaling_policy" "was_policy_up" {
  name = "was_policy_up"
  scaling_adjustment = 1
  adjustment_type = "ChangeInCapacity"
  cooldown = 10
  autoscaling_group_name = "${aws_autoscaling_group.was.name}"
}
# no cpu alarm with cloudwatch