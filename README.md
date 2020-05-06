# Dockerfile build
```
root@u1:~/kuberTest# docker build -t 1azytitan157/nginx-test:latest .
Sending build context to Docker daemon  58.88kB
Step 1/7 : FROM ubuntu
 ---> 1d622ef86b13
Step 2/7 : COPY ./install.sh /
 ---> Using cache
 ---> 60ca85c4c6ed
Step 3/7 : RUN chmod 755 /install.sh
 ---> Using cache
 ---> 4c3c24a5f928
Step 4/7 : RUN /install.sh
 ---> Using cache
 ---> 01576f28f3f1
Step 5/7 : COPY ./start.sh /
 ---> Using cache
 ---> fe3eb96a1dcd
Step 6/7 : RUN chmod 755 /start.sh
 ---> Using cache
 ---> 2e5d0072541e
Step 7/7 : CMD /start.sh
 ---> Using cache
 ---> 8214eebb1c36
Successfully built 8214eebb1c36
Successfully tagged 1azytitan157/nginx-test:latest
```
# 빌드한 이미지 실행 후 접속 및 nginx 실행 확인
```
root@u1:~/kuberTest# docker run -d --name latest -p 8888:80 1azytitan157/nginx-test:latest
a9c752dbef87a300399229171681b9937d35aefa7ad934b8ef6f654791b91193
```
# 이미지 실행 후 도커 상태 확인
```
docker ps -a
```

# dockerhub upload
```
root@u1:~/kuberTest# docker commit -m "nginx installed test latest" latest 1azytitan157/nginx-test:latest
sha256:79e0cfe952e1dbfe375ae7bb7eda518fa8b8462b891f9591158ac308f5ddccdc

root@u1:~/kuberTest# docker push 1azytitan157/nginx-test:latest
The push refers to repository [docker.io/1azytitan157/nginx-test]
b2632b4d1a0b: Pushed
b340dbfbcbe5: Layer already exists
fcdfc1b18df5: Layer already exists
53e7a7b33e87: Layer already exists
99f3140ae4d9: Layer already exists
d700ae1b2f97: Layer already exists
8891751e0a17: Layer already exists
2a19bd70fcd4: Layer already exists
9e53fd489559: Layer already exists
7789f1a3d4e9: Layer already exists
latest: digest: sha256:d8e24887f78ff552f9a2d0c800cf1572e3a46a6e2b657194a6fa44b0985bb8e5 size: 2399

```

--------------------


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
root@u1:~/kuberTest# docker commit -m "nginx installed test" new-nginx-test 1azytitan157/nginx-test:2
sha256:3aef11033372e7b9f9687d6280b67b4b54b2e3f094c15c388808a8142d7344bc
root@u1:~/kuberTest# docker push 1azytitan157/nginx-test
```
