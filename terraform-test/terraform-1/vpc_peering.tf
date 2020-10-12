#resource "aws_vpc" "peer" {
#  provider   = "aws.peer"
#  cidr_block = "106.0.0.0/16"
#}

#data "aws_caller_identity" "peer" {
#  provider = "aws.peer"
#}

# Requester's side of the connection.
resource "aws_vpc_peering_connection" "peer" {
  vpc_id        = "${aws_vpc.user06.id}"
  peer_vpc_id   = "vpc-04b2115fff18e8efc" #"${aws_vpc.peer.id}"
  peer_owner_id = "${var.peer_owner_id}"
  peer_region   = "eu-west-2"
  #auto_accept   = false

  tags = {
    #Side = "Requester"
    Name = "user06-peering"
  }
}
