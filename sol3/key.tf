resource "aws_key_pair" "ktcloudkey" {
  key_name   = "kt-cloud-key"
  public_key = file(var.PATH_TO_PUBLIC_KEY)
}
