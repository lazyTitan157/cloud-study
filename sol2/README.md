### 1. Git 레포지터리 https://bitbucket.org/jgnam/webapp.git 의 소스코드를 Jenkins를 통해 Build하시오.
* 빌드 성공(Build에 성공한 Jenkins UI 이미지)
![5 2 1](https://user-images.githubusercontent.com/8167433/81764244-156f8e00-950c-11ea-9d55-545ec69f5e2c.png)

### 2. 위 1번에서 Build된 war 파일을 jenkins로 톰캣서버로 전송하는 파이프 라인을 만드시오.
* 파이프라인 작성(Jenkins UI 이미지)
![5 2 2](https://user-images.githubusercontent.com/8167433/81764333-4bad0d80-950c-11ea-9418-ae4c357448ec.png)
* 톰캣서버 전송 성공(Tomcat Server의 Admin화면)
![5 2 2(tomcat)](https://user-images.githubusercontent.com/8167433/81764446-8f077c00-950c-11ea-89d3-8897a7d19c09.png)

###위를 구현할때 Jenkins에서 생성한 모든 /var/lib/jenkins/jobs/{job이름}/config.xml 파일을 /sol2폴더에 첨부하시오.
* 소스코드 빌드한 job의 config.xml
https://github.com/lazyTitan157/jenkins-test/blob/master/sol2/webapp-job/config.xml
```
vagrant@jenkins1:~$ cat /var/lib/jenkins/jobs/webapp-job/config.xml
```
* 파이프라인의 config.xml 
https://github.com/lazyTitan157/jenkins-test/blob/master/sol2/webapp-deployment/config.xml
```
vagrant@jenkins1:~$ cat /var/lib/jenkins/jobs/webapp-deployment/config.xml
```

### (가점2.5%) git레포지터리를 fork떠서 작업함
https://bitbucket.org/1azytitan/webapp/src/master/
### (가점1.5%) git 소스 코드를 수정하고 이를 업그레이든 한 결과를 README.md에 표현할 경우
* before
![5 2 plus2 before](https://user-images.githubusercontent.com/8167433/81764818-63d15c80-950d-11ea-8e4f-57376a55a9b4.png)
* after ( LG > KT )
![5 2 plus2 after](https://user-images.githubusercontent.com/8167433/81764843-7055b500-950d-11ea-9901-a8d50843fc56.png)
* bitbucket 소스 코드 수정결과 UI
![5 2 plus2](https://user-images.githubusercontent.com/8167433/81764924-9b400900-950d-11ea-9c65-0b0634c99179.png)
