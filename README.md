spring-cloud 基本框架 
1.	eureka server
a.	@EnableEurekaServer  
b.	没有后端缓存，每一个实例注册后需要向注册中心发送心跳，server 本身也是一个client，指定自己的server:registerWithEureka: false
2.	eureka client
a.	@EnableEurekaClient
b.	要注明自己的客户端名字，等一下到server上去能找到自己
3.	rest+ribbon
a.	spring cloud两种调用方式；
i.	一种是restTemplate+ribbon 
ii.	feign
b.	ribbon 负载均衡，feign默认继承ribbon
c.	启动类加注解@EnableDiscoveryClient
 展开源码
4.	feign
a.	pom中添加feign依赖；启动类@EnableFeignClients
b.	@FeignClient(value = "service-hi")
c.	定义一个feignserveice的接口，接口中用@FeignClient(value = "service-hi") 指明调用的eureka的客户端，然后controller再调用feignservice的方法，就和调用本地方法一样了；
5.	hystrix
a.	服务与服务之间可以是rpc，springcloud中是使用resttemplate +ribbob 或者是feign来调用，由于网络和自身的原因，服务不能百分百可用，这个时候如果出现一个服务故障，将会导致server的线程用光；瘫痪，然后服务与服务之间有是有关联调用的，这个时候整个可能瘫痪。所以有了断路器模式
b.	cuit breaker pattern，比如 5秒20次 断路器将打开
c.	ribbon中使用断路器：启动类添加@EnableHystrix；service方法上添加@HystrixCommand注解， @HystrixCommand(fallbackMethod = "hiError")会执行快速失败，直接返回一组字符串，而不是等待响应超时
d.	feign中使用断路器：feign.hystrix.enabled=true，在feign中的接口中的方法配置@FeignClient(value = "service-hi",fallback = SchedualServiceHiHystric.class)回调方法是一个类，继承自己。
6.	zuul
a.	路由和转发，
b.	@EnableZuulProxy
c.	路由的功能
i.	
 展开源码
d.	过滤的功能
i.	pre,routing,post,error路由的时机
7.	spring cloud config
a.	存放位置：本地和Git仓库
b.	config server config client
c.	配置类@EnableConfigServer；config 的git地址，分支，用户名密码等配置
d.	config-client 指明URi ，从server读取配置，然后server从git中读取配置
8.	高可用分布式配置中心spring cloud config
a.	将config-server注册到eureka中，config-client也注册到eureka中，server配置多个实例就实现了高可用
9.	消息总线 spring cloud bus
a.	spring-cloud-starter-bus-amqp，配置好rabbitmq的一些配置文件
b.	http://localhost:8881/actuator/bus-refresh  这样刷一下就能重新读取配置文件
c.	另外，/actuator/bus-refresh接口可以指定服务，即使用"destination"参数，比如 “/actuator/bus-refresh?destination=customers:**” 即刷新服务名为customers的所有服务
10.	服务链路追踪 spring cloud sleuth
a.	分布式的连串调用分析，减让支持zipkin；
b.	下载zipkin的jar，启动为zipkin的server；yml中制定zipkin server 的地址
11.	高可用服务注册中心
a.	eureka server的集群化
b.	两个server相互注册，通过服务器的浓郁度来增加可靠性。
12.	断路器监控 hystrix dashboard
a.	Hystrix Dashboard是作为断路器状态的一个组件，提供了数据监控和友好的图形化界面
b.	@EnableHystrixDashboard
c.	http://localhost:8762/actuator/hystrix.stream
d.	localhost:8762/hystrix  图形界面，输入上述网址
13.	断路器聚合追踪hystrix turbine
a.	@EnableTurbine这就需要聚合所以服务的Hystrix Dashboard的数据了
14.	spring colud gate way

