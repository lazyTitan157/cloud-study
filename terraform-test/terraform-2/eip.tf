resource "aws_eip" "nat_user106_1a" {
    vpc = true
    tags = {
        Name = "user106-eip"
    }
}