FROM openjdk:8-jre-alpine

MAINTAINER 芋道源码 <admin@iocoder.cn>

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' > /etc/timezone

# 创建工作目录
WORKDIR /app

# 复制 jar 包
COPY yudao-module-order-server.jar /app/app.jar

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
