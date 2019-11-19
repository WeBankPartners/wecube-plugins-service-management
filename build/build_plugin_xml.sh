#!/bin/sh

if [ $# -ne 1 ]
  then
    echo "Usage: build_plugin_xml.sh VERSION"
    exit 1
fi

plugin_version=$1

echo `pwd`
sed -i "s~{{VERSION}}~$plugin_version~g" register.xml