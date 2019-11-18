#!/bin/sh

if [ $# -ne 1 ]
  then
    echo "Usage: build_plugin_xml.sh VERSION"
    exit 1
fi

version=$1
sed -i 's/{{VERSION}}/$version/g' ../register.xml