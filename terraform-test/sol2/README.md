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
  default = "4"
}
```
### ./sol2 폴더에 Terraform코드를 저장할 것
* https://github.com/lazyTitan157/terraform_Test/tree/master/sol2

### Terraform을 통한 instance 생성 과정을 ./sol2/README.md에 저장하시오.
```
vagrant@terraformBox:~/terraform_Test/sol2$ ta

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
aws_key_pair.ktcloudkey: Creating...
aws_key_pair.ktcloudkey: Creation complete after 1s [id=kt-cloud-key]
aws_instance.example[3]: Creating...
aws_instance.example[2]: Creating...
aws_instance.example[1]: Creating...
aws_instance.example[0]: Creating...

...
Apply complete! Resources: 5 added, 0 changed, 0 destroyed.
            "public_ip": "13.125.249.252",
            "public_ip": "54.180.144.188",
            "public_ip": "52.79.240.67",
            "public_ip": "13.125.210.129",
```

### 요건에 맞는 테스트 과정을 ./sol2/README.md에 저장하시오.
* 4개서버에 아래와 같이 접속하여 nginx 테스트
```
vagrant@terraformBox:~/terraform_Test/sol2$ ssh -i ~/mykey ubuntu@13.125.249.252
Last login: Wed May 13 09:36:51 2020 from 59.13.4.75
ubuntu@ip-172-31-35-65:~$ ps -ef | grep nginx
root      2464     1  0 09:37 ?        00:00:00 nginx: master process /usr/sbin/nginx -g daemon on; master_process on;
www-data  2466  2464  0 09:37 ?        00:00:00 nginx: worker process
ubuntu    2768  2755  0 09:40 pts/0    00:00:00 grep --color=auto nginx
ubuntu@ip-172-31-35-65:~$ curl localhost
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

### Terraform을 통한 insntace 삭제 과정을 ./sol2/README.md에 저장하시오.
```
vagrant@terraformBox:~/terraform_Test/sol2$ td
terraform destroy -auto-approve

aws_key_pair.ktcloudkey: Refreshing state... [id=kt-cloud-key]
aws_instance.example[3]: Refreshing state... [id=i-0e12906119c2091e8]
aws_instance.example[2]: Refreshing state... [id=i-06280691b0692c428]
aws_instance.example[0]: Refreshing state... [id=i-01c97d9a773f146f4]
aws_instance.example[1]: Refreshing state... [id=i-031527f440ada9f62]
aws_instance.example[1]: Destroying... [id=i-031527f440ada9f62]
aws_instance.example[0]: Destroying... [id=i-01c97d9a773f146f4]
aws_instance.example[3]: Destroying... [id=i-0e12906119c2091e8]
aws_instance.example[2]: Destroying... [id=i-06280691b0692c428]
aws_instance.example[1]: Still destroying... [id=i-031527f440ada9f62, 10s elapsed]
aws_instance.example[0]: Still destroying... [id=i-01c97d9a773f146f4, 10s elapsed]
aws_instance.example[3]: Still destroying... [id=i-0e12906119c2091e8, 10s elapsed]
aws_instance.example[2]: Still destroying... [id=i-06280691b0692c428, 10s elapsed]
aws_instance.example[1]: Still destroying... [id=i-031527f440ada9f62, 20s elapsed]
aws_instance.example[0]: Still destroying... [id=i-01c97d9a773f146f4, 20s elapsed]
aws_instance.example[3]: Still destroying... [id=i-0e12906119c2091e8, 20s elapsed]
aws_instance.example[2]: Still destroying... [id=i-06280691b0692c428, 20s elapsed]
aws_instance.example[1]: Still destroying... [id=i-031527f440ada9f62, 30s elapsed]
aws_instance.example[0]: Still destroying... [id=i-01c97d9a773f146f4, 30s elapsed]
aws_instance.example[3]: Still destroying... [id=i-0e12906119c2091e8, 30s elapsed]
aws_instance.example[2]: Still destroying... [id=i-06280691b0692c428, 30s elapsed]
aws_instance.example[0]: Destruction complete after 30s
aws_instance.example[3]: Destruction complete after 30s
aws_instance.example[2]: Destruction complete after 30s
aws_instance.example[1]: Destruction complete after 30s
aws_key_pair.ktcloudkey: Destroying... [id=kt-cloud-key]
aws_key_pair.ktcloudkey: Destruction complete after 0s

Destroy complete! Resources: 5 destroyed.
```
