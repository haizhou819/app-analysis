#!/bin/bash
#export the environment
export LANG=zh_CN.GB18030

program="app-analysis"
DEPLOY_DIR=`pwd`
sysdir=$DEPLOY_DIR/WEB-INF

res=`ps aux|grep java|grep $program|grep -v grep|awk '{print $2}'`
if [ -n "$res" ]; then
	echo "app-analysis is already running!!!"
else
#run the program
CLASSPATH=$sysdir/classes:$JAVA_HOME/lib/rt.jar:$JAVA_HOME/lib/dt.jar
for jar in `ls $sysdir/lib/*.jar`
do
        CLASSPATH="$CLASSPATH":"$jar"
done
java  -Xms200M -Xmx1024M -classpath $CLASSPATH com.iflytek.xfyun.client.Client > /dev/null &
echo $!>app-analysis.pid
fi