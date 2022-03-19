## zEureka 服务的注册与发现

### 简介

![img](https://gitee.com/kongxiangjin/images/raw/master/img/86ee6b94cd1551cce34ea0b02357bc15_918x564.png)

​     `Spring Cloud Eureka`来实现服务治理。

​    ` Spring Cloud Eureka`是`Spring Cloud Netflix`项目下的服务治理模块。而`Spring Cloud Netflix`项目是`Spring Cloud`的子项目之一，主要内容是对Netflix公司一系列开源产品的包装，它为`Spring Boot`应用提供了自配置的`Netflix OSS`整合。通过一些简单的注解，开发者就可以快速的在应用中配置一下常用模块并构建庞大的分布式系统。它主要提供的模块包括：服务发现（`Eureka`），断路器（`Hystrix`），智能路由（`Zuul`），客户端负载均衡（`Ribbon`）等。

### 搭建Eureka服务注册中心

#### 1、Spring Cloud版本管理

在父项目中加入统一的版本管理，具体的版本号参照官网

```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.3.RELEASE</version>
        <relativePath /> <!-- lookup parent from repository -->
</parent>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>Hoxton.SR8</version>
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
    <artifactId>spring-cloud-learning</artifactId>
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
-  在项目中进行了版本管理，子项目不需要写版本号

#### 3、修改配置文件

```yml
server:
  port: 8001
  servlet:
    context-path: /eureka
spring:
  application:
    name: eureka-server-8001
eureka:
  instance:
    hostname: eureka-server-8001
  client:
    #是否从其他实例获取服务注册信息，因为这是一个单节点的EurekaServer，不需要同步其他的EurekaServer节点的数据，所以设置为false；
    fetch-registry: false
    #表示是否向eureka注册服务，即在自己的eureka中注册自己，默认为true，此处应该设置为false；
    register-with-eureka: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

- `defaultZone`: 属性区分大小写，并且需要使用驼峰式大小写，因为`ServiceUrl` 属性是`Map<String, String>`

  ![1601220208925](.\doc\iamges\1601220208925.png)

#### 4、配置启动类注解

```java
@SpringBootApplication
@EnableEurekaServer
public class MicroServiceCloudEurekaServerApplication8001 {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCloudEurekaServerApplication8001.class, args);
    }
}

```

#### 5、访问测试

访问配置的`Eureka` 服务的地址，http://localhost:8001/eureka/

若没有配置context-path，则直接访问http://localhost:8001

![1600960337980](.\doc\iamges\1600960337980.png)



### 微服务注册客户端构建

#### 1、新建项目添加依赖

```xml
 <parent>
        <artifactId>spring-cloud-learning</artifactId>
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
    #将自己注册到注册中心
    register-with-eureka: true
    service-url:
      defaultZone: http://localhost:8001/eureka/eureka/
#      注意：这里是两个eureka，我没写错。这里的url取决于eureka服务注册中心的配置
spring:
  application:
    name: eurka-client-8090
#spring:
#  profiles:
#    active: replicas

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

#### 常见bug

![img](https://gitee.com/kongxiangjin/images/raw/master/img/1601d9a364fc23a8eba5d5603293a410_1683x345.png)

- 碰上这个报错，第一时间要取检查defaultZone配置的Url是否正确
- 其次，要保证启动顺序，服务注册中心先启动，微服务后启动
- 服务注册中心是否默认配置了Spring Security？（我们上一节没有配置相关安全认证）
- 注意eureka客户端:`http://localhost:8761/eureka/eureka/`,有两个eureka。第一个eureka是`server.servlet.context-path: /eureka`。可以通过配置改变的；第二个eureka是服务注册的服务端点





### 集群配置

如果是集群版的，不能用ip，且application.name必须是同一个,register-with-eureka、fetch-registry必须设置成true,参考配置，底部有配置好的附件

```xml
server:
  port: 8001
  servlet:
    context-path: /eureka
spring:
  application:
    name: eureka-server
#eureka:
#  instance:
#    hostname: eureka-server-8001
#  client:
#    #是否从其他实例获取服务注册信息，因为这是一个单节点的EurekaServer，不需要同步其他的EurekaServer节点的数据，所以设置为false；
#    fetch-registry: false
#    #表示是否向eureka注册服务，即在自己的eureka中注册自己，默认为true，此处应该设置为false；
#    register-with-eureka: false
#    service-url:
#      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
eureka:
  instance:
    hostname: eureka8001
    health-check-url: http://${eureka.instance.hostname}:${server.port}/${server.servlet.context-path}/actuator/health
  client:
    #从其他两个实例同步服务注册信息
    fetch-registry: true
    #向其他eureka注册当前eureka实例
    register-with-eureka: true
    service-url:
      defaultZone: http://eureka8002:8002/eureka/eureka/,http://eureka8003:8003/eureka/eureka/


server:
  port: 8002
  servlet:
    context-path: /eureka
spring:
  application:
    name: eureka-server
eureka:
  instance:
    hostname: eureka8002
    health-check-url: http://${eureka.instance.hostname}:${server.port}/${server.servlet.context-path}/actuator/health
  client:
    #从其他两个实例同步服务注册信息
    fetch-registry: true
    #向其他eureka注册当前eureka实例
    register-with-eureka: true
    service-url:
      defaultZone: http://eureka8001:8001/eureka/eureka/,http://eureka8003:8003/eureka/eureka/






server:
  port: 8003
  servlet:
    context-path: /eureka
spring:
  application:
    name: eureka-server
eureka:
  instance:
    hostname: eureka8003
    health-check-url: http://${eureka.instance.hostname}:${server.port}/${server.servlet.context-path}/actuator/health
  client:
    #从其他两个实例同步服务注册信息
    fetch-registry: true
    #向其他eureka注册当前eureka实例
    register-with-eureka: true
    service-url:
      defaultZone: http://eureka8001:8001/eureka/eureka/,http://eureka8002:8002/eureka/eureka/




```

**修改hosts文件**

前面单机的时候 eureka注册中心实例名称 是localhost，现在是集群，不能三个实例都是localhost，这里复杂的办法是搞三个虚拟机，麻烦，这里有简单办法，直接配置本机hosts，来实现本机域名映射；

hosts，加配置 

```
127.0.0.1 eureka8001 eureka8002 eureka8003
```



很多人写的垃圾东西，搭建过程遇到各种报错之后，就将fetch-registry和register-with-eureka的值改成false，来解决问题。这两个值之所以设置为true，目的是让eureka集群之间实现互相注册，互相心跳健康状态，从而达到集群的高可用



**为eureka server加入actuator**

spring-boot-starter-actuator是为Spring Boot服务提供相关监控信息的包。因为我们的eureka server要互相注册，并检查彼此的健康状态，所以这个包必须带上。

```x'm'l
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



![image-20220320020119802](https://gitee.com/kongxiangjin/images/raw/master/img/image-20220320020119802.png)
