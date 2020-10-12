variable "amazon_linux" {
    # Amazon Linux AMI 2017.03.1 (HVM), SSD Volume Type - ami-4af5022c 
    # London - HVM(SSD)EBS 지원64비트
    default = "ami-f976839e"
}
variable "user106_keyname" {
    #user06 key
    default = "user106-key"
}
variable "alb_account_id" {
    # eu-west-2 
    default = "652711504416"
}
variable "peer_owner_id"{
    default = "560570842358"
}