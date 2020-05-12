terraform {
  backend "s3" {
    bucket = "ktclouds3bucket2"
    key    = "terraform.tfstate"
  }
}
