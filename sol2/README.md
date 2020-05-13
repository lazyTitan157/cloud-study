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
```
vagrant@jenkins1:~$ cat /var/lib/jenkins/jobs/webapp-job/config.xml
```
```
<?xml version='1.1' encoding='UTF-8'?>
<project>
  <actions/>
  <description></description>
  <keepDependencies>false</keepDependencies>
  <properties/>
  <scm class="hudson.plugins.git.GitSCM" plugin="git@4.2.2">
    <configVersion>2</configVersion>
    <userRemoteConfigs>
      <hudson.plugins.git.UserRemoteConfig>
        <url>https://1azytitan@bitbucket.org/1azytitan/webapp.git</url>
      </hudson.plugins.git.UserRemoteConfig>
    </userRemoteConfigs>
    <branches>
      <hudson.plugins.git.BranchSpec>
        <name>*/master</name>
      </hudson.plugins.git.BranchSpec>
    </branches>
    <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
    <submoduleCfg class="list"/>
    <extensions/>
  </scm>
  <canRoam>true</canRoam>
  <disabled>false</disabled>
  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
  <triggers/>
  <concurrentBuild>false</concurrentBuild>
  <builders>
    <hudson.tasks.Shell>
      <command>mvn package</command>
    </hudson.tasks.Shell>
  </builders>
  <publishers>
    <hudson.tasks.ArtifactArchiver>
      <artifacts>multi3/target/*.war</artifacts>
      <allowEmptyArchive>false</allowEmptyArchive>
      <onlyIfSuccessful>false</onlyIfSuccessful>
      <fingerprint>false</fingerprint>
      <defaultExcludes>true</defaultExcludes>
      <caseSensitive>true</caseSensitive>
      <followSymlinks>false</followSymlinks>
    </hudson.tasks.ArtifactArchiver>
  </publishers>
  <buildWrappers>
    <hudson.plugins.ws__cleanup.PreBuildCleanup plugin="ws-cleanup@0.38">
      <deleteDirs>false</deleteDirs>
      <cleanupParameter></cleanupParameter>
      <externalDelete></externalDelete>
      <disableDeferredWipeout>false</disableDeferredWipeout>
    </hudson.plugins.ws__cleanup.PreBuildCleanup>
  </buildWrappers>
</project>
```
* 파이프라인의 config.xml
```
vagrant@jenkins1:~$ cat /var/lib/jenkins/jobs/webapp-deployment/config.xml
```
```
<?xml version='1.1' encoding='UTF-8'?>
<project>
  <actions/>
  <description></description>
  <keepDependencies>false</keepDependencies>
  <properties/>
  <scm class="hudson.scm.NullSCM"/>
  <canRoam>true</canRoam>
  <disabled>false</disabled>
  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
  <triggers>
    <jenkins.triggers.ReverseBuildTrigger>
      <spec></spec>
      <upstreamProjects>webapp-job, </upstreamProjects>
      <threshold>
        <name>SUCCESS</name>
        <ordinal>0</ordinal>
        <color>BLUE</color>
        <completeBuild>true</completeBuild>
      </threshold>
    </jenkins.triggers.ReverseBuildTrigger>
  </triggers>
  <concurrentBuild>false</concurrentBuild>
  <builders>
    <hudson.plugins.copyartifact.CopyArtifact plugin="copyartifact@1.44">
      <project>webapp-job</project>
      <filter></filter>
      <target></target>
      <excludes></excludes>
      <selector class="hudson.plugins.copyartifact.StatusBuildSelector"/>
      <doNotFingerprintArtifacts>false</doNotFingerprintArtifacts>
    </hudson.plugins.copyartifact.CopyArtifact>
  </builders>
  <publishers>
    <hudson.plugins.deploy.DeployPublisher plugin="deploy@1.15">
      <adapters>
        <hudson.plugins.deploy.tomcat.Tomcat7xAdapter>
          <credentialsId>a28be75f-26e2-40db-9fa6-85aafd9fe31a</credentialsId>
          <url>http://172.22.101.111:8080/</url>
          <path></path>
        </hudson.plugins.deploy.tomcat.Tomcat7xAdapter>
      </adapters>
      <contextPath>finfra_app_$BUILD_NUMBER</contextPath>
      <war>**/*.war</war>
      <onFailure>false</onFailure>
    </hudson.plugins.deploy.DeployPublisher>
  </publishers>
  <buildWrappers>
    <hudson.plugins.ws__cleanup.PreBuildCleanup plugin="ws-cleanup@0.38">
      <deleteDirs>false</deleteDirs>
      <cleanupParameter></cleanupParameter>
      <externalDelete></externalDelete>
      <disableDeferredWipeout>false</disableDeferredWipeout>
    </hudson.plugins.ws__cleanup.PreBuildCleanup>
  </buildWrappers>
</project>
```
