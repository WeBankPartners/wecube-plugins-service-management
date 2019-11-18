project_name=service-mgmt
version=v1.0
current_dir=$(shell pwd)
date=$(shell date +%Y%m%d%H%M%S)
remote_docker_image_registry=ccr.ccs.tencentyun.com/webankpartners/wecube-app
s3_server_url=http://10.10.10.1:9000
s3_access_key=access_key
s3_secret_key=secret_key

clean:
	rm -rf $(current_dir)/target
	rm -rf $(current_dir)/service-management-ui/dist

.PHONY:build
build:
	mkdir -p repository
	docker run --rm --name service-mgmt-build -e SASS_BINARY_SITE=https://npm.taobao.org/mirrors/node-sass -v /data/wecube_repository:/usr/src/mymaven/repository -v $(current_dir)/build/maven_settings.xml:/usr/share/maven/ref/settings-docker.xml  -v $(current_dir):/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn -U clean install -Dmaven.test.skip=true -s /usr/share/maven/ref/settings-docker.xml dependency:resolve

image:
	docker build -t $(project_name):$(version) .
	
.PHONY:package
package:
	rm -rf package
	mkdir -p package
	cd package && docker save -o image.tar $(project_name):$(version) 
	
	sh build/build_plugin_xml.sh $(version)
	cd package && cp ../register.xml .
	
	git checkout master && git pull
	rm -rf service-management-ui/dist/*
	cd service-management-ui && npm install && npm run plugin
	cd service-management-ui && zip -r ui.zip service-management-ui/dist/
	cp service-management-ui/ui.zip package/ui.zip
	
	cp src/main/resources/database/init.sql package/init.sql
	
	cd package && rm -f service-mgmt-$(version).zip
	cd package && zip service-mgmt-$(version).zip package/*
	
	docker run --name minio-client-service-mgmt -v `pwd`/package:/package -itd --entrypoint=/bin/sh minio/mc
	docker exec minio-client-service-mgmt mc config host add wecubeS3 $(s3_server_url) $(s3_access_key) $(s3_secret_key) wecubeS3
	docker exec minio-client-service-mgmt mc cp /package/$(project_name)-$(version).zip wecubeS3/wecube-plugin-package-bucket
	docker rm -f minio-client-service-mgmt
	
	docker rmi $(project_name):$(version)
