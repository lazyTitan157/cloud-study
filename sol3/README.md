# Usage
### 3. ./sol3 폴더에 Terraform코드를 저장할 것
* 1. 유럽(아일랜드) Region에 Nginx가 설치된 인스턴스를 생성(로그인 가능해야 함.)
* 2. 3개의 인스턴스를 생성하시오.(resource "aws_instance"는 3개 사용하나 각각 다른 Availability Zones에 있어야 함.)
- 아래 ./sol3 폴더의 Terraform코드에서 확인 가능
 + https://github.com/lazyTitan157/terraform_Test/tree/master/sol3

### 4. Terraform을 통한 instance 생성 과정을 ./sol3/README.md에 저장하시오.
```
vagrant@terraformBox:~/terraform_Test/sol3$ ta

terraform destroy -auto-approve
terraform init
terraform apply -auto-approve
cat terraform.tfstate|grep public_ip|grep -v associate


Destroy complete! Resources: 0 destroyed.

Initializing the backend...

Initializing provider plugins...

The following providers do not have any version constraints in configuration,
so the latest version was installed.

To prevent automatic upgrades to new major versions that may contain breaking
changes, it is recommended to add version = "..." constraints to the
corresponding provider blocks in configuration, with the constraint strings
suggested below.

* provider.aws: version = "~> 2.61"

Terraform has been successfully initialized!

You may now begin working with Terraform. Try running "terraform plan" to see
any changes that are required for your infrastructure. All Terraform commands
should now work.

If you ever set or change modules or backend configuration for Terraform,
rerun this command to reinitialize your working directory. If you forget, other
commands will detect it and remind you to do so if necessary.
aws_vpc.main: Creating...
aws_key_pair.ktcloudkey: Creating...
aws_key_pair.ktcloudkey: Creation complete after 3s [id=kt-cloud-key]
aws_vpc.main: Still creating... [10s elapsed]
aws_vpc.main: Still creating... [20s elapsed]
aws_vpc.main: Creation complete after 22s [id=vpc-0fd17b53090e650a2]
aws_internet_gateway.main-gw: Creating...
aws_subnet.main-private-3: Creating...
aws_subnet.main-private-1: Creating...
aws_subnet.main-public-3: Creating...
aws_subnet.main-private-2: Creating...
aws_subnet.main-public-1: Creating...
aws_subnet.main-public-2: Creating...
aws_security_group.allow-ssh: Creating...
aws_subnet.main-private-2: Creation complete after 7s [id=subnet-063dbfe535b95c111]
aws_subnet.main-private-3: Creation complete after 7s [id=subnet-059a68728a7b57b0e]
aws_subnet.main-private-1: Creation complete after 8s [id=subnet-0e553d1ff8639a45e]
aws_subnet.main-public-3: Creation complete after 10s [id=subnet-0a500303e327533

...
Apply complete! Resources: 17 added, 0 changed, 0 destroyed.
            "public_ip": "34.240.87.61",
            "public_ip": "54.170.178.200",
            "public_ip": "34.247.188.205",
            "map_public_ip_on_launch": false,
            "map_public_ip_on_launch": false,
            "map_public_ip_on_launch": false,
            "map_public_ip_on_launch": true,
            "map_public_ip_on_launch": true,
            "map_public_ip_on_launch": true,

```

### 5. 요건에 맞는 테스트 과정을 ./sol3/README.md에 저장하시오.
```
# cat terraform.tfstate|grep public_ip
# ssh -i kt-cloud-key ubuntu@public_IP
## curl localhost
```

### 6. Terraform을 통한 insntace 삭제 과정을 ./sol3/README.md에 저장하시오.
```
terraform destroy -auto-approve
```
