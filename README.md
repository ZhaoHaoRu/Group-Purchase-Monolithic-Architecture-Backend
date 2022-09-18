# README
### Introduction
本项目是“交我团”团购平台的单体架构版本后端，采用了`spring boot`框架，使用了`Redis` + `RabbitMQ`实现了秒杀逻辑，数据库采用了`MySQL`存储基本数据+`MongoDB`存储图片base64编码。项目具体实现的功能详见`Group-Purchase-mobile-frontend`的`README`。

### 算法
详见 https://maipdf.cn/pdf/d94589168740@pdf

### 环境依赖
JDK8 + MySQL8.028 + Redis3.0.504 + RabbitMQ3.10.6 + MongoDB6.0.0

### 安装与运行
1. clone项目
```shell
git clone https://github.com/ZhaoHaoRu/Group-Purchase-Monolithic-Architecture-Backend.git
# 打开项目所在文件夹
cd GroupBuy
```
2. 修改`application.properties`中`spring.datasource.username`、`spring.datasource.password`、`spring.rabbitmq.username`和`spring.rabbitmq.password`等必要信息
3. 执行`Maven clean`和`Maven install`
4. 项目成功运行后，可以在swagger提供的文档中查看接口信息

