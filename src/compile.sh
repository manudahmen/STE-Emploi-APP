#!/bin/bash
CATALINA_HOME=/opt/apache-tomcat-5.5.27
JAVA_HOME=/usr/lib/jvm/java-6-sun-1.6.0.10
WEBAPP_SRC_DIR=$CATALINA_HOME/webapps/ste-emploi/WEB-INF/src
WEBAPP_CLASSES_DIR=$CATALINA_HOME/webapps/ste-emploi/WEB-INF/classes
CLASSPATH=$CATALINA_HOME/webapps/ste-emploi/WEB-INF/lib/\
mysql-connector-java-5.1.6-bin.jar:$CATALINA_HOME/common/lib/\
servlets-default.jar:$CATALINA_HOME/webapps/ste-emploi/WEB-INF\
/lib/struts-core-1.3.10.jar:$CATALINA_HOME/webapps/common/lib/jsp-api.jar:\
$CATALINA_HOME/common/lib/servlet-api.jar:$CATALINA_HOME/webapps/\
ste-emploi/WEB-INF/lib/commons-beanutils-1.8.0.jar
export CATALINA_HOME JAVA_HOME CLASSPATH WEBAPP_SRC_DIR WEBAPP_CLASSES_DIR
javac -g -cp $CLASSPATH -sourcepath $WEBAPP_SRC_DIR -d $WEBAPP_CLASSES_DIR \
steemploi/service/*.java \
steemploi/persistance/*.java \
steemploi/ui/*.java \
steemploi/service/events/*.java \
com/myapp/struts/*.java com/myapp/struts/events/*.java
