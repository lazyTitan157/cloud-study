# Dockerfile build
```
root@u1:~/kuberTest# docker build -t 1azytitan157/nginx-test:1 .
Sending build context to Docker daemon  58.88kB
Step 1/7 : FROM ubuntu
latest: Pulling from library/ubuntu
d51af753c3d3: Pull complete
fc878cd0a91c: Pull complete
6154df8ff988: Pull complete
fee5db0ff82f: Pull complete
Digest: sha256:747d2dbbaaee995098c9792d99bd333c6783ce56150d1b11e333bbceed5c54d7
Status: Downloaded newer image for ubuntu:latest
 ---> 1d622ef86b13
Step 2/7 : COPY ./install.sh /
 ---> 60ca85c4c6ed
Step 3/7 : RUN chmod 755 /install.sh
 ---> Running in ec613cd63f3f
Removing intermediate container ec613cd63f3f
 ---> 4c3c24a5f928
Step 4/7 : RUN /install.sh
 ---> Running in 4b66daf317e8

...

Successfully built 8214eebb1c36
Successfully tagged 1azytitan157/nginx-test:1
```
# 앞서 빌드한 Docker image 확인
```
root@u1:~/kuberTest# docker images
REPOSITORY                TAG                 IMAGE ID            CREATED             SIZE
1azytitan157/nginx-test   1                   8214eebb1c36        2 minutes ago       154MB
```
# 빌드한 이미지 실행 후 접속 및 nginx 실행 확인
```
root@u1:~/kuberTest# docker run -it 1azytitan157/nginx-test:1
root@e8d32c35ed31:/# ps -ef | grep nginx
root          8      1  0 07:57 ?        00:00:00 nginx: master process nginx
www-data      9      8  0 07:57 ?        00:00:00 nginx: worker process
www-data     10      8  0 07:57 ?        00:00:00 nginx: worker process
www-data     11      8  0 07:57 ?        00:00:00 nginx: worker process
root         16     12  0 07:58 pts/0    00:00:00 grep --color=auto nginx
```
```
root@u1:~/kuberTest# docker run --name new-nginx-test -d -p 8081:80 1azytitan157/nginx-test:1
930e9135d6697a632e2ab901b3ad8ac4c289e2790d9d05014bb13d5c5735f07b
root@u1:~/kuberTest# docker ps -a
CONTAINER ID        IMAGE                       COMMAND                  CREATED             STATUS                      PORTS               NAMES
930e9135d669        1azytitan157/nginx-test:1   "/bin/sh -c /start.sh"   3 seconds ago       Exited (0) 3 seconds ago                        new-nginx-test
```

# 이미지 실행 후 도커 상태 확인
```
root@u1:~/kuberTest# docker ps
CONTAINER ID        IMAGE                       COMMAND                  CREATED              STATUS              PORTS               NAMES
e8d32c35ed31        1azytitan157/nginx-test:1   "/bin/sh -c /start.sh"   About a minute ago   Up About a minute
```

# 도커허브에 이미지 업로드
```
root@u1:~/kuberTest# docker commit -m "nginx installed" vigorous_antonelli 1azytitan157/nginx-test

root@u1:~/kuberTest# docker push 1azytitan157/nginx-test
The push refers to repository [docker.io/1azytitan157/nginx-test]
b340dbfbcbe5: Pushed
fcdfc1b18df5: Pushed
53e7a7b33e87: Pushed
99f3140ae4d9: Pushed
d700ae1b2f97: Pushed
8891751e0a17: Mounted from library/ubuntu
2a19bd70fcd4: Mounted from library/ubuntu
9e53fd489559: Mounted from library/ubuntu
7789f1a3d4e9: Mounted from library/ubuntu
1: digest: sha256:7395ad7c491d2e6d1d48b43b7a04d212a5b0f22a0d2435d720fb21e947149e4c size: 2192
d9ab52febc37: Pushed
b340dbfbcbe5: Layer already exists
fcdfc1b18df5: Layer already exists
53e7a7b33e87: Layer already exists
99f3140ae4d9: Layer already exists
d700ae1b2f97: Layer already exists
8891751e0a17: Layer already exists
2a19bd70fcd4: Layer already exists
9e53fd489559: Layer already exists
7789f1a3d4e9: Layer already exists
latest: digest: sha256:7ef1437f1af5b1487fdda96cf676026cb0805959165107c1bbed40bba6384930 size: 2399
```
```
root@u1:~/kuberTest# docker commit -m "nginx installed test" new-nginx-test 1azytitan157/nginx-test:2
sha256:3aef11033372e7b9f9687d6280b67b4b54b2e3f094c15c388808a8142d7344bc
root@u1:~/kuberTest# docker push 1azytitan157/nginx-test
```
