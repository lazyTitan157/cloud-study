# Git 레포지터리 https://bitbucket.org/jgnam/base.git 의 소스코드를 Jenkins를 통해 Build하시오.
* 빌드 결과(Build에 성공한 Jenkins UI 이미지)
![5 1 1](https://user-images.githubusercontent.com/8167433/81762390-75b00100-9507-11ea-91c1-abde92955bd6.png)

# 위 1번에서 Build된 Artifact를 jenkins로 Artifactory서버로 전송하시오.
* 빌드 결과(Build에 성공한 Jenkins UI 이미지)
![5 1 2](https://user-images.githubusercontent.com/8167433/81762444-97a98380-9507-11ea-9dba-fd960cbf52e5.png)
* 전송 결과(전송에 성공한 Artifactory UI 이미지)
![5 1 3](https://user-images.githubusercontent.com/8167433/81762488-b27bf800-9507-11ea-8a82-7bcbfd0332e0.png)

# 위를 구현할때 Jenkins에서 생성한 /var/lib/jenkins/jobs/{job이름}/config.xml 파일을 /sol1폴더에 첨부하시오.
```
vagrant@jenkins1:~$ cat /var/lib/jenkins/jobs/jenkins-test/config.xml
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
        <url>https://1azytitan@bitbucket.org/1azytitan/base.git</url>
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
    <hudson.plugins.gradle.Gradle plugin="gradle@1.36">
      <switches></switches>
      <tasks></tasks>
      <rootBuildScriptDir></rootBuildScriptDir>
      <buildFile></buildFile>
      <gradleName>(Default)</gradleName>
      <useWrapper>false</useWrapper>
      <makeExecutable>false</makeExecutable>
      <useWorkspaceAsHome>false</useWorkspaceAsHome>
      <wrapperLocation></wrapperLocation>
      <passAllAsSystemProperties>false</passAllAsSystemProperties>
      <projectProperties></projectProperties>
      <passAllAsProjectProperties>false</passAllAsProjectProperties>
    </hudson.plugins.gradle.Gradle>
  </builders>
  <publishers/>
  <buildWrappers>
    <org.jfrog.hudson.gradle.ArtifactoryGradleConfigurator plugin="artifactory@3.6.2">
      <deployMaven>false</deployMaven>
      <deployIvy>false</deployIvy>
      <deployBuildInfo>true</deployBuildInfo>
      <includeEnvVars>false</includeEnvVars>
      <deployerCredentialsConfig>
        <credentialsId></credentialsId>
        <overridingCredentials>false</overridingCredentials>
        <ignoreCredentialPluginDisabled>false</ignoreCredentialPluginDisabled>
      </deployerCredentialsConfig>
      <resolverCredentialsConfig>
        <credentialsId></credentialsId>
        <overridingCredentials>false</overridingCredentials>
        <ignoreCredentialPluginDisabled>false</ignoreCredentialPluginDisabled>
      </resolverCredentialsConfig>
      <ivyPattern>[organisation]/[module]/ivy-[revision].xml</ivyPattern>
      <enableIssueTrackerIntegration>false</enableIssueTrackerIntegration>
      <aggregateBuildIssues>false</aggregateBuildIssues>
      <artifactPattern>[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]</artifactPattern>
      <useMavenPatterns>false</useMavenPatterns>
      <artifactDeploymentPatterns>
        <includePatterns></includePatterns>
        <excludePatterns></excludePatterns>
      </artifactDeploymentPatterns>
      <discardOldBuilds>false</discardOldBuilds>
      <passIdentifiedDownstream>false</passIdentifiedDownstream>
      <discardBuildArtifacts>true</discardBuildArtifacts>
      <asyncBuildRetention>false</asyncBuildRetention>
      <deploymentProperties></deploymentProperties>
      <useArtifactoryGradlePlugin>true</useArtifactoryGradlePlugin>
      <allowPromotionOfNonStagedBuilds>false</allowPromotionOfNonStagedBuilds>
      <filterExcludedArtifactsFromBuild>true</filterExcludedArtifactsFromBuild>
      <resolverDetails>
        <artifactoryName>myArtifactory</artifactoryName>
        <artifactoryUrl>http://jm2:8081/artifactory</artifactoryUrl>
        <resolveReleaseRepository>
          <keyFromText></keyFromText>
          <keyFromSelect></keyFromSelect>
          <dynamicMode>false</dynamicMode>
        </resolveReleaseRepository>
        <stagingPlugin/>
      </resolverDetails>
      <defaultPromotionTargetRepository></defaultPromotionTargetRepository>
      <deployerDetails>
        <artifactoryName>myArtifactory</artifactoryName>
        <artifactoryUrl>http://jm2:8081/artifactory</artifactoryUrl>
        <deployReleaseRepository>
          <keyFromText></keyFromText>
          <keyFromSelect>gradle-dev-local</keyFromSelect>
          <dynamicMode>false</dynamicMode>
        </deployReleaseRepository>
        <stagingPlugin>
          <pluginName>None</pluginName>
        </stagingPlugin>
        <userPluginKey>None</userPluginKey>
      </deployerDetails>
      <deployArtifacts>true</deployArtifacts>
      <envVarsPatterns>
        <includePatterns></includePatterns>
        <excludePatterns>*password*,*psw*,*secret*,*key*,*token*</excludePatterns>
      </envVarsPatterns>
      <customBuildName></customBuildName>
      <overrideBuildName>false</overrideBuildName>
    </org.jfrog.hudson.gradle.ArtifactoryGradleConfigurator>
  </buildWrappers>
```
