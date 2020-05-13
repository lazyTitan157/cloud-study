# Usage
### 1. 유럽(아일랜드) Region에 Nginx가 설치된 인스턴스를 생성(로그인 가능해야 함.)

### 2. 3개의 인스턴스를 생성하시오.(resource "aws_instance"는 3개 사용하나 각각 다른 Availability Zones에 있어야 함.)

### 3. ./sol3 폴더에 Terraform코드를 저장할 것

### 4. Terraform을 통한 instance 생성 과정을 ./sol3/README.md에 저장하시오.
```
terraform init
terraform apply -auto-approve
```

### 5. 요건에 맞는 테스트 과정을 ./sol3/README.md에 저장하시오.
* security group 80 port open
```
# cat terraform.tfstate|grep public_ip
# ssh -i kt-cloud-key ubuntu@public_IP
## curl localhost
```

### 6. Terraform을 통한 insntace 삭제 과정을 ./sol3/README.md에 저장하시오.
```
terraform destroy -auto-approve
```
