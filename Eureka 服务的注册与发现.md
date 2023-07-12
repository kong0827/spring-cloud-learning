## Eureka 服务的注册与发现

### 简介

![img](https://kong-blog.oss-cn-shanghai.aliyuncs.com/86ee6b94cd1551cce34ea0b02357bc15_918x564-20230712140035380-20230712140040178.png)

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

  ![1601220208925](https://kong-blog.oss-cn-shanghai.aliyuncs.com/img/1601220208925.png)

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

![1600960337980](https://kong-blog.oss-cn-shanghai.aliyuncs.com/1600960337980.png)



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
    name: eureka-client-8090
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

![1601221216625](https://kong-blog.oss-cn-shanghai.aliyuncs.com/img/1601221216625.png)

#### 常见bug

![img](https://kong-blog.oss-cn-shanghai.aliyuncs.com/img/1601d9a364fc23a8eba5d5603293a410_1683x345.png)

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

在eureka集群搭建过程中，fetch-registry和register-with-eureka一定设置为true

很多人写的垃圾东西，搭建过程遇到各种报错之后，就将fetch-registry和register-with-eureka的值改成false，来解决问题。这两个值之所以设置为true，目的是让eureka集群之间实现互相注册，互相心跳健康状态，从而达到集群的高可用



#### 注意事项

需要保证 eureka.instance.hostname eureka服务的实例名称在集群中保证唯一。

需要保证 eureka.client.service-url.defaultZone 的主机名（或IP）要唯一。如果是 Eureka 集群在同一个主机上时（学习和开发时），通过配置 hosts ，保证主机名的唯一。

win10 环境修改，在 C:\Windows\System32\drivers\etc 的 hosts 文件修改：

127.0.0.1  eureka7001.com
127.0.0.1  eureka7002.com
127.0.0.1  eureka7003.com

##### 错误配置示例
示例2
eureka.instance.hostname 实例名称在集群中保证唯一，但是 defaultZone 的主机名称不是唯一的。

尽管端号是不同的，Eureka Server可以正常启动，但是无法组合成集群。

![在这里插入图片描述](https://kong-blog.oss-cn-shanghai.aliyuncs.com/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hpYW9qaW4yMWNlbg==,size_16,color_FFFFFF,t_70-20230712144545885.png)

造成的结果的，只有备分一个主机：

![在这里插入图片描述](https://kong-blog.oss-cn-shanghai.aliyuncs.com/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hpYW9qaW4yMWNlbg==,size_16,color_FFFFFF,t_70.png)





**为eureka server加入actuator**

spring-boot-starter-actuator是为Spring Boot服务提供相关监控信息的包。因为我们的eureka server要互相注册，并检查彼此的健康状态，所以这个包必须带上。

```x'm'l
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



![image-20220320020119802](https://kong-blog.oss-cn-shanghai.aliyuncs.com/img/image-20220320020119802.png)



#### 其他需要注意的点：

出现 unavailable-replicas 问题，首先要去检查一下你的health-check-url是否能正常响应。如果没有设置context-path,默认是：`http://ip:端口/actuator/health`。UP状态表示处于可用状态。
![image-20220320231323712](https://kong-blog.oss-cn-shanghai.aliyuncs.com/img/image-20220320231323712.png)

如果健康检查没有问题：
1.是否开启了register-with-eureka=true和fetch-registry=true
2.eureka.client.serviceUrl.defaultZone配置项的地址，不能使用localhost，要使用ip或域名。或者可以通过hosts或者DNS解析的主机名称hostname。
3.spring.application.name要一致，不配置也可以，配置了要一致
4.默认情况下，Eureka 使用 hostname(如：eureka8001, eureka8002, eureka8003) 进行服务注册，以及服务信息的显示（eureka web 页面），那如果我们希望使用 IP 地址的方式，该如何配置呢？

答案就是`eureka.instance.prefer-ip-address=true`。当设置prefer-ip-address: true时 ，修改配置`defaultZone:http://你的IP:9001/eureka/`。如果此时你仍然使用`http://peer1:8761/eureka`会导致健康检查失败。





### Eureka安全认证（服务端）

在此之前，我们的微服务向服务注册中心注册都是使用的公开访问权限，在实际的生产应用中，这很危险。因为随便的一个什么人，知道这个服务注册地址，都可以写一个服务注册到上面。通常我们需要使用eureka server对注册服务进行一个Spring Security的HttpBasic安全认证，虽然这个认证很简陋，但是能起到“防君子不防小人的”作用。

在zimug-server-eureka项目的pom.xml 中添加 Spring-Security 的依赖包

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

然后在zimug-server-eureka项目的 application.yml中加上认证的配置信息（用户名和密码）：

```
spring:
  security:
    user:
      name: kxj0827
      password: centerpwd
```

然后在zimug-server-eureka项目增加Spring Security 配置类：

```
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();
        // 支持httpBasic认证方式
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
```

- 重新启动注册中心，浏览器访问服务注册中心，此时浏览器会提示你输入用户名和密码，输入正确后才能继续访问 Eureka 提供的管理页面



### Eureka安全认证(客户端)

在 Eureka Server开启认证后，所有的服务（包括eureka-server本身）向服务注册中心注册的时候也要加上认证的用户名和密码信息：

```
defaultZone: 
http://kxj0827:centerpwd@eureka8001:8001/eureka/eureka/,http://kxj0827:centerpwd@eureka8003:8003/eureka/eureka/
```

有需要向服务注册中心注册的微服务都要加上，否则无法正确的注册，会被安全策略拦截。即：eureka-server的defaultZone配置都要修改



### Eureka的健康检查

![image-20220320234214607](https://kong-blog.oss-cn-shanghai.aliyuncs.com/img/image-20220320234214607.png)

- 在Status栏显示着UP，表示该服务及其多实例处于状态正常。其它取值DOWN、OUT_OF_SERVICE、UNKNOWN等均表示该服务处于不可被请求的状态，只有UP状态的微服务会被请求。
- 由于Eureka Server与Eureka Client之间使用心跳机制来确定Eureka Client（微服务实例）的状态。也就是说，当服务器端与客户端的心跳保持正常，服务的状态就会始终保持“UP”状态。所以说该UP状态不能完全说明该服务可以正常响应HTTP请求，只能说明Eureka Server与Eureka Client之间存在正常心跳。
- 基于上一点原因，Spring Boot Actuator提供了/health端点，该端点可展示应用程序的健康信息，只有将该端点中的健康状态传播到Eureka Server就可以了，实现这点很简单，只需为微服务配置如下内容：

```
#开启健康检查（需要spring-boot-starter-actuator依赖）（默认就是开启的）
eureka.client.healthcheck.enabled = true
```

如果需要更细粒度健康检查，可实现com.netflix.appinfo.HealthCheckHandler接口 。 EurekaHealthCheckHandler 已实现了该接口。

**正常情况下，当微服务的心跳消失，健康检查失败后，eureka server会将该服务从服务列表中剔除。表示该服务下线了。** 但是一些异常情况下，eureka不会剔除服务，比如：eureka自我保护模式被开启的情况下。



### Eureka自我保护模式

访问Eureka主页时，如果看到这样一段大红色的句子，那么表明Eureka的自我保护模式被启动了。

![img](https://img.kancloud.cn/8b/34/8b34fc93e8624ba80bcd797878a46af7_1880x79.png)

- 默认情况下，如果在15分钟内超过85%的客户端节点都没有正常的心跳，那么Eureka就认为这些客户端与注册中心出现了网络故障(比如网络故障或频繁的启动关闭客户端)，Eureka Server自动进入自我保护模式。
- 进入保护状态后，eureka server不再剔除任何服务，当网络故障恢复后，该节点自动退出自我保护模式。避免将仍在正常运行的服务，因为eureka server服务的网络问题导致其他业务服务无法正常发现及调用。
- 默认情况下，如果Eureka Server在一定时间内（默认90秒）没有接收到某个微服务实例的心跳，Eureka Server将会移除该实例。但是当网络分区故障发生时，微服务与Eureka Server之间无法正常通信，而微服务本身是正常运行的，此时不应该移除这个微服务，所以引入了自我保护机制。

> 当个别服务健康检查失败，eureka server认为是该服务正常下线了，将其服务从列表中剔除。但是当85%以上服务都收不到心跳了，eureka server会认为是自己出了问题，就开启保护模式，不再将服务从列表中剔除。从而保障微服务系统的可用性。

```
eureka:
  server:
    #自我保护模式，当出现网络分区故障、频繁的开启关闭客户端、eureka在短时间内丢失过多客户端时，会进入自我保护模式，即一个服务长时间没有发送心跳，eureka也不会将其删除，默认为true
    enable-self-preservation: true
    #eureka server清理无效节点的时间间隔，默认60000毫秒，即60秒
    eviction-interval-timer-in-ms: 60000
    #阈值更新的时间间隔，单位为毫秒，默认为15 * 60 * 1000
    renewal-threshold-update-interval-ms: 15 * 60 * 1000
    #阈值因子，默认是0.85，如果阈值比最小值大，则自我保护模式开启
    renewal-percent-threshold: 0.85
    #清理任务程序被唤醒的时间间隔，清理过期的增量信息，单位为毫秒，默认为30 * 1000
    delta-retention-timer-interval-in-ms: 30000
```

- 通常而言，生产环境建议开启自我保护模式，从而保障生产环境的健壮性。
- 测试环境可以将自我保护模式关闭，因为我们可能经常的启停微服务，导致心跳消失。经常性的触发自我保护模式，导致该下线的服务没下线。

### 关闭 Eureka 的自我保护模式

可以使用eureka.server.enable-self-preservation=false来禁用自我保护模式，

- 通常而言，生产环境建议开启自我保护模式，从而保障生产环境的健壮性。
- 开发测试环境可以将自我保护模式关闭，因为我们可能经常的启停微服务，导致心跳消失。经常性的触发自我保护模式，导致该下线的服务没下线，导致调试异常。

关闭自我保护模式，需要在服务端和客户端配置。

```
服务端配置：
eureka:
  server:
    enable-self-preservation: false
    #eureka server清理无效节点的时间间隔，默认60000毫秒，即60秒
    eviction-interval-timer-in-ms: 60000 # 单位毫秒

客户端配置：
# 心跳检测检测与续约时间
# 测试时将值设置设置小些，保证服务关闭后注册中心能及时踢出服务
eureka:
  instance:
    lease-renewal-interval-in-seconds: 5  #默认30秒
    lease-expiration-duration-in-seconds: 10   #默认90秒
```

配置说明

```
lease-renewal-interval-in-seconds 每间隔5s，向服务端发送一次心跳，证明自己依然”存活“。# 默认30秒
lease-expiration-duration-in-seconds  告诉服务端，如果我10s之内没有给你发心跳，就代表我“死”了，请将我踢掉。# 默认90秒
```
