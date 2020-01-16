#!/bin/sh

db_host=$1
db_port=$2
db_schema=$3

mkdir -p /log
java -jar /service-management/service-management.jar  --server.address=0.0.0.0 --server.port=21000 \
--spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver \
--spring.datasource.url="jdbc:mysql://${db_host}:${db_port}/${db_schema}?characterEncoding=utf8&serverTimezone=Asia/Shanghai" \
--spring.datasource.username=$4 \
--spring.datasource.password=$5 \
--service.management.wecube-core-address=$6 >> /log/service-management.log
