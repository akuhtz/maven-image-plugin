# maven-image-plugin
Maven plugin to add text to images during the build

#Usage

```
<plugin>
    <groupId>org.bidib.jbidib.de.akquinet.jbosscc.maven</groupId>
    <artifactId>image-maven-plugin</artifactId>
    <version>1.1</version>
    <executions>
        <execution>
            <phase>process-resources</phase>
            <goals>
                <goal>add-text</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <targetFormat>png</targetFormat>
        <sourceImage>src/main/resources/icons/splash.png</sourceImage>
        <targetImage>target/classes/icons/splash.png</targetImage>
        <defaults>
            <fontName>Sans Serif</fontName>
            <fontStyle>1</fontStyle>
            <fontSize>10</fontSize>
            <fontColor>BLACK</fontColor>
        </defaults>
        <texts>
            <text>
                <text>${project.version} (${buildNumber})</text>
                <offsetX>200</offsetX>
                <offsetY>10</offsetY>
            </text>
        </texts>
    </configuration>
</plugin>
```


#Release Build with Maven

* Don't use the latest version of maven-release-plugin because MRELEASE-875 causes problems. <br>
  I ended up using version 2.3.2 and this worked for me under Windows 7.

* Add the following to git config: <br>
```
  git config status.displayCommentPrefix true
```
 
* Add git binaries to your PATH.

* Use maven-3.0.5

* Do not use the <gpg.useagent> property. Add the password for the maven-gpg-plugin manually.

* Steps:<br>
```
  mvn release:prepare -DdryRun
  mvn deploy // just to make sure deployment works
  
  mvn release:clean
  mvn release:prepare -Dusername=<your_github_username> -Dpassword=<your_password>
  mvn release:perform -Dusername=<your_github_username> -Dpassword=<your_password>
```
