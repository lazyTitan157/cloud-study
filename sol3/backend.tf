terraform {
  backend "s3" {
    bucket = "ktclouds3bucket3"
    key    = "terraform.tfstate"
  }
}
