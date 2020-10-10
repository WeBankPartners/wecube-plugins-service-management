FROM platten/alpine-oracle-jre8-docker:latest
LABEL maintainer = "Webank Open-Platform Team"
ADD target/service-management-0.0.1-SNAPSHOT.jar  /service-management/service-management.jar
ADD build/start.sh /scripts/start.sh
RUN chmod +x /scripts/start.sh
ADD build/tomcat_exporter.tar /scripts/
CMD ["/bin/sh","-c","/scripts/start.sh $DB_HOST $DB_PORT $DB_SCHEMA $DB_USER $DB_PWD $CORE_ADDR $S3_ENDPOINT $S3_ACCESS_KEY $S3_SECRET_KEY"]
