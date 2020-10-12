# Accepter's side of the connection.
resource "aws_vpc_peering_connection_accepter" "peer" {
  #provider                  = "aws.peer"
  vpc_peering_connection_id = "pcx-000c3a20ed71fcd39" #${aws_vpc_peering_connection.peer.id}"
  auto_accept               = true

  tags = {
    Side = "Accepter"
    Name = "user106-peering"
  }
}