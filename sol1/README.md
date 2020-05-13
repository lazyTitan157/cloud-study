### Git 레포지터리 https://bitbucket.org/jgnam/base.git 의 소스코드를 Jenkins를 통해 Build하시오.
* 빌드 결과(Build에 성공한 Jenkins UI 이미지)
![5 1 1](https://user-images.githubusercontent.com/8167433/81762390-75b00100-9507-11ea-91c1-abde92955bd6.png)

### 위 1번에서 Build된 Artifact를 jenkins로 Artifactory서버로 전송하시오.
* 빌드 결과(Build에 성공한 Jenkins UI 이미지)
![5 1 2](https://user-images.githubusercontent.com/8167433/81762444-97a98380-9507-11ea-9dba-fd960cbf52e5.png)
* 전송 결과(전송에 성공한 Artifactory UI 이미지)
![5 1 3](https://user-images.githubusercontent.com/8167433/81762488-b27bf800-9507-11ea-8a82-7bcbfd0332e0.png)

### 위를 구현할때 Jenkins에서 생성한 /var/lib/jenkins/jobs/{job이름}/config.xml 파일을 /sol1폴더에 첨부하시오.
* https://github.com/lazyTitan157/jenkins-test/blob/master/sol1/config.xml
```
vagrant@jenkins1:~$ cat /var/lib/jenkins/jobs/jenkins-test/config.xml
```


### (가점2.5%) git레포지터리를 fork떠서 작업함
- https://bitbucket.org/1azytitan/base/src/master/
### (가점1.5%) git 소스 코드를 수정하고 이를 업그레이든 한 결과를 README.md에 표현할 경우
* 소스 수정 후, 빌드 결과
![5 1 plus b](https://user-images.githubusercontent.com/8167433/81766486-31c1f980-9511-11ea-94a8-a5f50a9eb151.png)
* 소스 수정 후, 전송 결과
![5 1 plus after](https://user-images.githubusercontent.com/8167433/81766461-2242b080-9511-11ea-87c6-7f5ad63c19e1.png)

* bitbucket 소스 코드 수정결과 UI
![5 1 plus](https://user-images.githubusercontent.com/8167433/81766443-1656ee80-9511-11ea-8057-4a84ccd5fbae.png)
