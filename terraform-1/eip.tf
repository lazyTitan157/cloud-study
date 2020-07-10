resource "aws_eip" "nat_user06_1a" {
    vpc = true
    tags = {
        Name = "user06-eip"
    }
}
