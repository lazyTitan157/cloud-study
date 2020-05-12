resource "aws_key_pair" "kt-cloud-key" {
  key_name   = "kt-cloud-key"
  public_key = "~/kt-cloud-key.pub"
  #file(var.PATH_TO_PUBLIC_KEY)
}

resource "aws_instance" "example" {
  ami           = "ami-00edfb46b107f643c"
  #lookup(var.AMIS, var.AWS_REGION)
  instance_type = "t2.micro"
  key_name      = "kt-cloud-key"
  #aws_key_pair.kt-cloud-key.key_name

  provisioner "file" {
    source      = "script.sh"
    destination = "/tmp/script.sh"
  }
  provisioner "remote-exec" {
    inline = [
      "chmod +x /tmp/script.sh",
      "sudo /tmp/script.sh"
    ]
  }
  provisioner "local-exec" {
    command = "echo private ip ${aws_instance.example.*.private_ip} >> private_ips.txt"
  }

  connection {
    host        = coalesce(self.public_ip, self.private_ip)
    user        = "ubuntu"
    #var.INSTANCE_USERNAME
    private_key = "~/kt-cloud-key"
    #file(var.PATH_TO_PRIVATE_KEY)
  }
  tags = {
  }
}
