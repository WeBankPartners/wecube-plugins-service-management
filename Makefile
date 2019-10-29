project_name=service-management
version=v0.1
current_dir=$(shell pwd)
date=$(shell date +%Y%m%d%H%M%S)
remote_docker_image_registry=ccr.ccs.tencentyun.com/webankpartners/wecube-app

clean:
	rm -rf $(current_dir)/target

.PHONY:build
build:
	mkdir -p repository
	docker run --rm --name service-management-build -v /data/wecube_repository:/usr/src/mymaven/repository -v $(current_dir)/build/maven_settings.xml:/usr/share/maven/ref/settings-docker.xml  -v $(current_dir):/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn -U clean install -Dmaven.test.skip=true -s /usr/share/maven/ref/settings-docker.xml dependency:resolve

image:
	docker build -t $(project_name):$(version) .

push:
	docker tag  $(project_name):$(version) $(remote_docker_image_registry):$(date)-$(version)
	docker push $(remote_docker_image_registry):$(date)-$(version)