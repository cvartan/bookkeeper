FROM openjdk:21-jdk
WORKDIR /opt/bk/bin
COPY target/bookkeeper-0.0.1-SNAPSHOT.jar /opt/bk/bin/bookkeeper.jar
EXPOSE 8021
ENTRYPOINT [ "java","-jar","/bookkeeper.jar" ]