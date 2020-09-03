#!/bin/sh

db_host=$1
db_port=$2
db_schema=$3
db_username=$4
db_password=$5
core_addr=$6
s3_endpoint=$7
s3_access_key=$8
s3_secret_key=$9

/bin/sh /scripts/tomcat_exporter/start.sh
mkdir -p /log
java -Djava.security.egd=file:/dev/urandom -Duser.timezone=Asia/Shanghai \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=18082 \
-Dcom.sun.management.jmxremote.rmi.port=18082 \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-jar /service-management/service-management.jar  --server.address=0.0.0.0 --server.port=21000 \
--spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver \
--spring.datasource.url=jdbc:mysql://${db_host}:${db_port}/${db_schema}?characterEncoding=utf8\&serverTimezone=Asia\/Shanghai \
--spring.datasource.username=${db_username} \
--spring.datasource.password=${db_password} \
--service.management.wecube-core-address=${core_addr} \
--service.management.s3-endpoint=${s3_endpoint} \
--service.management.s3-access-key=${s3_access_key} \
--service.management.s3-secret-key=${s3_secret_key}  >> /log/service-management.log
