set ANT_HOME=c:\progra~1\ant\bin
set PATH=C:\Progra~1\Java\jdk1.6.0_14\bin;%PATH%
set CATALINA_HOME=C:\Progra~1\Tomcat
set JAVA_HOME=C:\Progra~1\Java\jdk1.6.0_14\bin
set WEBAPP_PATH=C:\Users\admin\workspace\ste-emploi\WebContent
set WEBAPP_OUTPUT=C:\Users\admin\workspace\ste-emploi\jsp_output
set CLASSPATH=%CATALINA_HOME%\webapps\ste-emploi\WEB-INF\lib\mysql-connector-java-5.1.6-bin.jar;%CATALINA_HOME%\webapps\common\lib\servlets-default.jar;%CATALINA_HOME%\webapps\ste-emploi\WEB-INF\lib\struts-core-1.3.10.jar;%CATALINA_HOME%\common\lib\jsp-api.jar;%CATALINA_HOME%\common\lib\servlet-api.jar;%CATALINA_HOME%\webapps\ste-emploi\WEB-INF\lib\commons-beanutils-1.8.0.jar	
	

%ANT_HOME%\ant.bat -Dtomcat.home=%CATALINA_HOME% -Dwebapp.home=%WEBAPP_PATH% -Doutput.home=%WEBAPP_OUTPUT% %1
pause
