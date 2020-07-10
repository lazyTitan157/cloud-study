variable "amazon_linux" {
    # Amazon Linux AMI 2017.03.1 (HVM), SSD Volume Type - ami-4af5022c
    default = "ami-097834fcb3081f51a"
}
variable "user06_keyname" {
    #user06 key
    default = "user06-key"
}
variable "alb_account_id" {
    #us-east-2
    default = "033677994240"
}
variable "peer_owner_id"{
    default = "560570842358"
}
variable "peer_cidr_block"{
    default = "106.0.0.0/16"
}