#!/bin/sh
mkdir -p /log
java -jar /service-management/service-management.jar  --server.address=0.0.0.0 --server.port=21000 \
--spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver \
--spring.datasource.url=$1 \
--spring.datasource.username=$2 \
--spring.datasource.password=$3 \
--service.management.wecube-core-address=$4 >> /log/service-management.log