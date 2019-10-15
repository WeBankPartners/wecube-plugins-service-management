from platten/alpine-oracle-jre8-docker:latest
LABEL maintainer = "Webank"
ADD wecube-plugins-service-management/target/service-management-0.0.1-SNAPSHOT.jar  /service-management/service-management.jar
ADD build/start.sh /scripts/start.sh
RUN chmod +x /scripts/start.sh
CMD ["/bin/sh","-c","/scripts/start.sh $DATA_SOURCE_URL $DB_USER $DB_PWD $CORE_ADDR"]