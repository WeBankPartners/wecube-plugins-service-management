# Service Management Plugin 服务管理插件

服务管理插件提供服务请求的定义以及流程管理， 是一个简化的ITSM。


## 技术实现
WeCube通过服务管理插件来实现不同角色的操作人员之间的协同。

此插件后端技术选型为Java + Spring boot, 前端技术选型为Vue + Vue-Router。

## 主要功能
服务管理插件包括以下功能：

- 模板管理：服务目录管理， 服务请求模板管理；
- 服务管理：服务请求管理，任务管理；

## 设计文档

[服务管理插件设计文档](wiki/design/service_management_design.md)

[服务管理插件前端设计文档](wiki/design/service_management_design_frontend.md)

## 编译打包
插件开发进行中...


## 插件运行
插件包制作完成后，需要通过WeCube的插件管理界面进行注册才能使用。运行插件的主机需提前安装好docker。