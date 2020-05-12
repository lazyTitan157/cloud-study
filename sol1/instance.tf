resource "aws_key_pair" "kt-cloud-key" {
  key_name   = "kt-cloud-key"
  public_key = file(var.PATH_TO_PUBLIC_KEY)
  lifecycle {
    ignore_changes = [tags]
  }
}

resource "aws_instance" "example" {
  ami           = lookup(var.AMIS, var.AWS_REGION)
  instance_type = "t2.micro"
  key_name      = aws_key_pair.kt-cloud-key.key_name

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
    user        = var.INSTANCE_USERNAME
    private_key = file(var.PATH_TO_PRIVATE_KEY)
  }
  tags = {
    # another way to define and use variables
    #Name = format("MyInstance%s", var.V1)
    #Name = "MyInstance${var.V1}"
    Name = join("", ["MyInstance", var.V1])
  }
}

variable "V1" {
  default = "V1"
}
