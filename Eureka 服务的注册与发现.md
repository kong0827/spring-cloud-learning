## Eureka 服务的注册与发现

### 简介

​     `Spring Cloud Eureka`来实现服务治理。

​    ` Spring Cloud Eureka`是`Spring Cloud Netflix`项目下的服务治理模块。而`Spring Cloud Netflix`项目是`Spring Cloud`的子项目之一，主要内容是对Netflix公司一系列开源产品的包装，它为`Spring Boot`应用提供了自配置的`Netflix OSS`整合。通过一些简单的注解，开发者就可以快速的在应用中配置一下常用模块并构建庞大的分布式系统。它主要提供的模块包括：服务发现（`Eureka`），断路器（`Hystrix`），智能路由（`Zuul`），客户端负载均衡（`Ribbon`）等。

### 搭建Eureka服务注册中心

#### 1、Spring Cloud版本管理

在父项目中加入统一的版本管理

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Hoxton.SR1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.2.1.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
     </dependencies>
</dependencyManagement>
```

#### 2、新建Spring Boot项目添加依赖

`Eureka` 服务注册中心也是机遇Spring Boot为基础搭建的微服务。

```xml
<parent>
    <artifactId>SpringCloud-Eureka</artifactId>
    <groupId>com.kxj.springcloud</groupId>
    <version>1.0-SNAPSHOT</version>
</parent>

<modelVersion>4.0.0</modelVersion>
<artifactId>micro-service-cloud-eureka-server-8001</artifactId>

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```

- 注册`Spring Boot`的版本与`Spring Cloud`的版本需对应 	

![1600959584551](.\doc\iamges\1600959584551.png)

-  在Spring Boot 1.0版本引入的包是`spring-cloud-starter-eureka`，在`Spring Boot 2.0`版本中不再使用，使用`spring-cloud-starter-netflix-eureka-server`
- 在项目中进行了版本管理，子项目不需要写版本号

#### 3、修改配置文件

```yml
server:
  port: 8001

eureka:
  instance:
    hostname: localhost # 该服务部署的主机名称
  client:
    register-with-eureka: false # 是否向Eureka注册服务，false表示自己端就是注册中心，不需要注册，职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false # 是否从其他实例获取服务注册信息，单节点情况下不需要同步其他节点数据
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
```

- `defaultZone`: 属性区分大小写，并且需要使用驼峰式大小写，因为`ServiceUrl` 属性是`Map<String, String>`

  ![1601220208925](.\doc\iamges\1601220208925.png)

#### 4、配置启动类

```java
@SpringBootApplication
// 开启Eureka
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

#### 5、访问测试

访问配置的`Eureka` 服务的地址，http://localhost:8001

![1600960337980](.\doc\iamges\1600960337980.png)



### 微服务注册客户端构建

#### 1、新建项目添加依赖

```xml
 <parent>
     <artifactId>SpringCloud-Eureka</artifactId>
     <groupId>com.kxj.springcloud</groupId>
     <version>1.0-SNAPSHOT</version>
</parent>
<modelVersion>4.0.0</modelVersion>

<artifactId>micro-service-cloud-eureka-client-8090</artifactId>

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

#### 2、修改配置文件

```yml
server:
  port: 8090
eureka:
  client:
    fetch-registry: false
    service-url:
      defaultZone: http://localhost:8001/eureka
spring:
  application:
    name: eurka-client-8090
```

#### 3、配置启动类

 在主启动类上添加@EnableEurekaClient注解 

```java
@SpringBootApplication
@EnableEurekaClient
public class MicroServiceCloudEurekaClient8090 {
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCloudEurekaClient8090.class, args);
    }
}
```

#### 4、启动测试

1. 先启动`Eureka Server` ，然后启动客户端
2. 访问` http://localhost:8001/ `

![1601221216625](.\doc\iamges\1601221216625.png)

