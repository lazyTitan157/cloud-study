terraform {
  backend "s3" {
    bucket = "ktclouds3bucket1"
    key    = "terraform.tfstate"
  }
}
