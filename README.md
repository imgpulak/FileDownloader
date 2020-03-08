# FileDownloader: 
A tool to which can be used to download data from multiple sources and protocols to local disk. 
The URLs will be passed as input from command line(space separated).
Ex: <code> <url1> <url2> <url3>

------
## Language 
openjdk 11.0.6 2020-01-14

------
## Build Tool 
Apache Maven

## Low Level Design
**Factory design pattern** has been used to get downloader based on protocol of urls.
It simplifies supporting new protocol in future. A class diagram has be attached below -

![Class Diagram](resources/ClassDiagram.jpg?raw=true "ClassDiagram")

------
## How to build?
1) Install Java.
2) Build this project using maven. 

------
## How to run?
1) Create config file(`<user_home>/.config/FileDownloader/config.properties`) and copy below lines -
~~~
# File Downloader directory settings
downloadDir=<user_home>/Downloads/FileDownloader
tempDir=/tmp/FileDownloader
~~~

2) Create log4j config file(`<user_home>/.config/FileDownloader/log4j.properties) and copy below lines -
~~~
log4j.rootLogger=INFO, console
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Threshold=INFO
log4j.appender.console.Target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.conversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p - %m%n
~~~

3) Run below command to test - 
~~~
java -Dfile.encoding=UTF-8 -classpath <JAR PATH> -Dlog4j.configuration=file://<user_home>/.config/FileDownloader/log4j.properties com.pulak.filedownloader.DownloadFile <url1> <url2> <url3>
~~~
* -classpath: Pass class path of all dependencies.
* -user_home: Path of your user home directory.

You may set -Dlog4j.configuration in Intellij as VM Option and run it from there too. 
Main class: com.pulak.filedownloader.DownloadFile

------
## How to run test suite? 
Run below command in project root directory -
~~~
mvn test
~~~ 

------
## How to check git commit? 
Run below command in project root directory -
~~~
git log
~~~ 

------
## TODO
* Testing for SFTP protocol.
* Unit test cases for all classed for better coverage.
* Parallel download using thread and synchronization.
