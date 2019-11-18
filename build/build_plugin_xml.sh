#!/bin/sh

if [ $# -ne 1 ]
  then
    echo "Usage: build_plugin_xml.sh VERSION"
    exit 1
fi

version=$1
current_path=`pwd`
echo pwd
sed -i 's/{{VERSION}}/$version/g' ${current_path}/../register.xml