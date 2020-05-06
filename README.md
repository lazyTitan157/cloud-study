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

#최종 성공한 명령어 및 결과
```
root@u1:~/kuberTest# docker build -t 1azytitan157/nginx-test:latest .                 Sending build context to Docker daemon  104.4kB
Step 1/6 : FROM ubuntu
 ---> 1d622ef86b13
Step 2/6 : COPY ./install.sh /
 ---> Using cache
 ---> 60ca85c4c6ed
Step 3/6 : RUN chmod 755 /install.sh
 ---> Using cache
 ---> 4c3c24a5f928
Step 4/6 : RUN /install.sh
 ---> Using cache
 ---> 01576f28f3f1
Step 5/6 : CMD [ "nginx", "-g", "daemon off;"]
 ---> Using cache
 ---> 85525e3b27f4
Step 6/6 : expose 80
 ---> Using cache
 ---> b07bb3f5e983
Successfully built b07bb3f5e983
Successfully tagged 1azytitan157/nginx-test:latest
root@u1:~/kuberTest# docker run -d --name latest-nginx-test -p 8888:80 1azytitan157/nginx-test:latest
04448e2976b0a74c72411ffe18e7800dc5de69dc0f10a7a26f73c660e1e50b68
root@u1:~/kuberTest# docker ps -a
CONTAINER ID        IMAGE                            COMMAND                  CREATED              STATUS                          PORTS                  NAMES
04448e2976b0        1azytitan157/nginx-test:latest   "nginx -g 'daemon of…"   2 seconds ago        Up 2 seconds                    0.0.0.0:8888->80/tcp   latest-nginx-test
d37a46ca44c7        c3fab651388d                     "/bin/sh -c /start.sh"   About a minute ago   Exited (0) About a minute ago                          latest-nginx
a9c752dbef87        8214eebb1c36                     "/bin/sh -c /start.sh"   12 minutes ago       Exited (0) 12 minutes ago                              latest
2f93bc7b3b5d        1azytitan157/nginx-test:2        "/bin/sh -c /start.sh"   15 minutes ago       Exited (0) 15 minutes ago                              n1
4f5b6fbdf26c        1azytitan157/nginx-test:2        "/bin/sh -c /start.sh"   16 minutes ago       Exited (0) 16 minutes ago                              new-nginx-test2
7071095ab99b        1azytitan157/nginx-test:2        "/bin/sh -c /start.sh"   50 minutes ago       Up 50 minutes                   80/tcp                 pull2
930e9135d669        1azytitan157/nginx-test:1        "/bin/sh -c /start.sh"   58 minutes ago       Exited (0) 58 minutes ago                              new-nginx-test
18234154d5a7        1azytitan157/nginx-test:1        "/bin/sh -c /start.sh"   About an hour ago    Up About an hour                                       pulled
9c51b71404d0        1azytitan157/practice:1          "/bin/bash"              About an hour ago    Created                                                pulled1
e8d32c35ed31        1azytitan157/nginx-test:1        "/bin/sh -c /start.sh"   About an hour ago    Up About an hour                                       vigorous_antonelli
76edf39ccff8        1azytitan157/nginx-test:1        "/bin/sh -c /start.sh"   About an hour ago    Exited (0) About an hour ago                           new-nginx
root@u1:~/kuberTest# docker commit -m "nginx installed test latest" latest-nginx-test 1azytitan157/nginx-test:latest
sha256:1aedc3898d9b8bf9978014e0567de3ea2dd2b4bd0526dd64fd4c8427e40e7d62
root@u1:~/kuberTest# docker push 1azytitan157/nginx-test:latest
The push refers to repository [docker.io/1azytitan157/nginx-test]
6de1bb463622: Pushed
53e7a7b33e87: Layer already exists
99f3140ae4d9: Layer already exists
d700ae1b2f97: Layer already exists
8891751e0a17: Layer already exists
2a19bd70fcd4: Layer already exists
9e53fd489559: Layer already exists
7789f1a3d4e9: Layer already exists
latest: digest: sha256:0956b4068e02760c6f4e459575dd04af7a0975a51dbd1e7e7ef2bd62eed7b7a3 size: 1985

```
