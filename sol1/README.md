# Usage
### Terraform을 통한 instance 생성 과정을 ./sol1/README.md에 저장하시오.
```
terraform init
terraform apply -auto-approve
```
### 요건에 맞는 테스트 과정을 ./sol1/README.md에 저장하시오.
* AWS콘솔에서 security group 설정 필요(22번, 80번 포트 오픈)
```
cat terraform.tfstate|grep public_ip
ssh -i kt-cloud-key ubuntu@public_IP

# AWS ubuntu 접속 후
ps -ef | grep nginx
curl localhost
```
### 테스트 실행 결과
* nginx process 확인
```
ubuntu@ip-172-31-35-52:~$ ps -ef | grep nginx
root      2465     1  0 00:40 ?        00:00:00 nginx: master process /usr/sbin/nginx -g daemon on; master_process on;
www-data  2467  2465  0 00:40 ?        00:00:00 nginx: worker process
ubuntu    2761  2748  0 00:41 pts/0    00:00:00 grep --color=auto nginx
```
* curl로 웹페이지 확인
```
ubuntu@ip-172-31-35-52:~$ curl localhost
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

### Terraform을 통한 insntace 삭제 과정을 ./sol1/README.md에 저장하시오.
```
terraform destroy -auto-approve
```
