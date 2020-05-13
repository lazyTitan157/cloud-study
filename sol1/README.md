# Usage
* cp ../terraform.tfvars ./
* cp ../mykey* ./

* terraform init

* terraform apply -auto-approve
* cat terraform.tfstate|grep public_ip
* ssh -i kt-cloud-key ubuntu@public_IP
 - security group 80 port open

```
ubuntu@ip-172-31-35-52:~$ ps -ef | grep nginx
root      2465     1  0 00:40 ?        00:00:00 nginx: master process /usr/sbin/nginx -g daemon on; master_process on;
www-data  2467  2465  0 00:40 ?        00:00:00 nginx: worker process
ubuntu    2761  2748  0 00:41 pts/0    00:00:00 grep --color=auto nginx
```
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
# instance 삭제
* terraform destroy -auto-approve
