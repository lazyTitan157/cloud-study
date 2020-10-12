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
aws_instance.example2: Still creating... [1m20s elapsed]
aws_instance.example2 (remote-exec): Setting up nginx (1.17.10-0ubuntu1) ...
aws_instance.example2 (remote-exec): Processing triggers for ufw (0.36-6) ...
aws_instance.example2 (remote-exec): Processing triggers for systemd (245.4-4ubuntu3) ...
aws_instance.example2 (remote-exec): Processing triggers for man-db (2.9.1-1) ...
aws_instance.example2 (remote-exec): Processing triggers for libc-bin (2.31-0ubuntu9) ...
aws_instance.example2: Creation complete after 1m26s [id=i-05173b5490461ac1a]

Apply complete! Resources: 17 added, 0 changed, 0 destroyed.
            "public_ip": "3.249.108.109",
            "public_ip": "54.75.71.133",
            "public_ip": "54.194.226.43",
            "map_public_ip_on_launch": false,
            "map_public_ip_on_launch": false,
            "map_public_ip_on_launch": false,
            "map_public_ip_on_launch": true,
            "map_public_ip_on_launch": true,
            "map_public_ip_on_launch": true,
```

### 5. 요건에 맞는 테스트 과정을 ./sol3/README.md에 저장하시오.
```
vagrant@terraformBox:~/terraform_Test/sol3$ ssh -i ~/mykey ubuntu@3.249.108.109
Last login: Wed May 13 10:37:13 2020 from 59.13.4.75
ubuntu@ip-10-0-1-207:~$ ps -ef | grep nginx
root        2528       1  0 10:37 ?        00:00:00 nginx: master process /usr/sbin/nginx -g daemon on; master_process on;
www-data    2529    2528  0 10:37 ?        00:00:00 nginx: worker process
ubuntu      2763    2753  0 10:38 pts/0    00:00:00 grep --color=auto nginx
ubuntu@ip-10-0-1-207:~$ curl localhost
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
    body {
        width: 35em;
        margin: 0 auto;
        font-family: Tahoma, Verdana, Arial, sans-serif;
    }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>
```

### 6. Terraform을 통한 insntace 삭제 과정을 ./sol3/README.md에 저장하시오.
```
vagrant@terraformBox:~/terraform_Test/sol3$ td
terraform destroy -auto-approve
...
Destroy complete! Resources: 17 destroyed.
```
