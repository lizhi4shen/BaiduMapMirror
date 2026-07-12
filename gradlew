#!/bin/sh
# Gradle Wrapper - will download gradle automatically

APP_HOME=$( cd "${APP_HOME:-./}" && pwd -P ) || exit

DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use
if [ -n "$JAVA_HOME" ] ; then
    JAVACMD=$JAVA_HOME/bin/java
else
    JAVACMD=java
fi

exec "$JAVACMD" ${DEFAULT_JVM_OPTS} -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
