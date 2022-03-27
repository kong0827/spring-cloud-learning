## Feign



### 源码分析

- FeignClientsRegistrar

  ```
  public void registerBeanDefinitions(AnnotationMetadata metadata,
        BeanDefinitionRegistry registry) {
     // 将@EnableFeignClients注解中的defaultConfiguration属性注册到一个缓存Map中
     registerDefaultConfiguration(metadata, registry);
  	 // 扫描所有的@FeignClient注解的接口，即扫描所有Feign接口
  	 // 将每个@FeignClient注解的configuration属性注册到一个缓存Map中
  	 // 根据@FeignClient注解元数据生成@FeignClientFactoryBean的BeanDefinition中，并将BeanDefinition注册到一个缓存Map中
  	registerFeignClients(metadata, registry);
  }
  ```



### 重试机制

### 错误码自定义

处理指定异常状态码

### 自定义拦截器

### Feign执行请求，记录·请求日志

Client





### 自定义拦截器和Convert

实现String转LocalDateTime



### UserHelper实战

设置和获取用户角色





### WebLog记录出入参等

