provider "aws" {
    region = "us-east-2"
}

provider "aws" {
  alias  = "peer"
  region = "eu-west-2"
  # Accepter's credentials.
}