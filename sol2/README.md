# Usage
### 유럽(아일랜드) Region에 Nginx가 설치된 인스턴스를 생성(로그인 가능해야 함.)
```
#vars.tf
variable "AWS_REGION" {
  default = "eu-west-1"
}
```
### 4개의 인스턴스를 생성하시오.(resource "aws_instance"는 1개만 사용)
```
#vars.tf
variable "instance_count" {
  default = "2"
}
```
### ./sol2 폴더에 Terraform코드를 저장할 것

### Terraform을 통한 instance 생성 과정을 ./sol2/README.md에 저장하시오.
```
terraform init
terraform apply -auto-approve
```

### 요건에 맞는 테스트 과정을 ./sol2/README.md에 저장하시오.

# cat terraform.tfstate|grep public_ip
# ssh -i kt-cloud-key ubuntu@public_IP
* security group 80 port open
## curl localhost

### Terraform을 통한 insntace 삭제 과정을 ./sol2/README.md에 저장하시오.
```
terraform destroy -auto-approve
```
