package com.example.demoamqp.DeadAmqpController.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;


    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";
    public static final String QUEUE_D = "QUEUE_D";

    public static final String EXCHANGE_A_TO_A = "exA_to_A";
    public static final String EXCHANGE_B_TO_B = "exB_to_B";
    public static final String EXCHANGE_C_TO_AC = "exC_to_AC";
    public static final String TOP_EXCHANGE_B = "TOP_EXCHANGE_b";
    public static final String EXCHANGE_D_TO_D = "exD_to_D";


    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";
    public static final String ROUTINGKEY_D = "spring-boot-routingKey_D";
    public static final String ROUTINGKEY_DD = "spring-boot-routingKey_DD";
    public static final String ROUTINGKEY_E = "spring-boot-routingKey_E";
    public static final String ROUTINGKEY_f = "spring-boot-routingKey_f";
    public static final String ROUTINGKEY_DEAD = "DEAD_ROUTINGKEY";

    /**
     * 设置rabbitmq的登录信息
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }





    /**
     *
     *
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange ExchangeA() {
        return new DirectExchange(EXCHANGE_A_TO_A);
    }
    @Bean
    public DirectExchange ExchangeB(){
        return  new DirectExchange(EXCHANGE_B_TO_B);
    }
    @Bean
    public DirectExchange ExchangeC(){
        return  new DirectExchange(EXCHANGE_C_TO_AC);
    }

    @Bean
    public DirectExchange ExchangeD(){
        return  new DirectExchange(EXCHANGE_D_TO_D);
    }

    @Bean
    public DirectExchange ExchangeDead(){
        return  new DirectExchange("DEAD_EXCHANGE");
    }
    /**
     * 获取队列A
     *
     * Queue的作用是存储消息，Queue的特性是先进传出的。
     *消费者通过  @RabbitListener(queues="队列名")监听某个或多个Queue从而获取Queue中的消息。
     *可以有多个消费者监听同一个Queue
     *当有多个消费者监听同一个Queue时，消息会平均分配给这多个消费者
     * 同过设置 prefetchCount 可以设置每次发给消费者的消息数量
     * @return
     */
    @Bean
    public Queue queueA() {

        /**
         * 创建Queue是参数说明：
         * name:对列名称 交换机通过RoutingKey与队列绑定
         *
         * durable:队列的声明默认是存放到内存中的，称为暂存队列，消息代理重启会丢失。如果想启             之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之             后会读取该数据库。但是队列持久化并不意味着消息持久化当消息代理重启后消息依旧会丢              失。
         *
         *exclusive：是否排外的，有两个作用，一：当连接关闭时connection.close()该队列是否会自动删除；二：该队列是否是私有的private，如果不是排外的，可以使用两个消费者都访问同一个队列，没有任何问题，如果是排外的，会对当前队列加锁，其他通道channel是不能访问的，如果强制访问会报异常：com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=405, reply-text=RESOURCE_LOCKED - cannot obtain exclusive access to locked queue 'queue_name' in vhost '/', class-id=50, method-id=20)一般等于true的话用于一个队列只能有一个消费者来消费的场景
         *
         *autoDelete：是否自动删除，当最后一个消费者断开连接之后队列是否自动被删除，可以通过RabbitMQ Management(页面客户端)，查看某个队列的消费者数量，当consumers = 0时队列就会自动删除
         *
         *
         */
        return new Queue(QUEUE_A, true); //队列持久

    }

    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, true); //队列持久

    }
    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C, true); //队列持久

    }
    //普通队列绑定死信交换机
    @Bean
    public Queue queueD() {
        Map<String, Object> arguments = new HashMap<>();
        //当队列消息长度大于最大长度、或者过期的等，将从队列中删除的消息推送到指定的交换机中去而不是丢弃掉,Features=DLX
        arguments.put("x-dead-letter-exchange", "DEAD_EXCHANGE");
        //删除的消息推送到指定交换机的指定路由键的队列中去, Feature=DLK
        arguments.put("x-dead-letter-routing-key", "DEAD_ROUTINGKEY");
        //设置队列中的所有消息的生存周期
        //arguments.put("x-message-ttl",1000*15);
        //当队列在指定的时间没有被访问(consume, basicGet, queueDeclare…)就会被删除,Features=Exp
        arguments.put("(x-expires",1000*60);
        // 限定队列的消息的最大值长度，超过指定长度将会把最早的几条删除掉， 类似于mongodb中的固定集合，例如保存最新的100条消息, Feature=Lim
        arguments.put("x-max-length",2);
        return new Queue(QUEUE_D, true, false, false, arguments);//队列持久
    }

    //死信队列
    @Bean
    public Queue deadQueue() {
        return new Queue("DEAD_QUEUE", true); //队列持久
    }




    @Bean
    public Binding bindingA() {

        return BindingBuilder.bind(queueA()).to(ExchangeA()).with(RabbitConfig.ROUTINGKEY_A);
    }
    @Bean
    public Binding bindingB() {

        return BindingBuilder.bind(queueB()).to(ExchangeB()).with(RabbitConfig.ROUTINGKEY_B);
    }

    @Bean
    public Binding bindingC() {

        return BindingBuilder.bind(queueC()).to(ExchangeC()).with(RabbitConfig.ROUTINGKEY_C);
    }
    @Bean
    public Binding bindingCTOA() {
        return BindingBuilder.bind(queueA()).to(ExchangeC()).with(RabbitConfig.ROUTINGKEY_D);
    }

    @Bean
    public Binding bindingD() {

        return BindingBuilder.bind(queueD()).to(ExchangeD()).with(RabbitConfig.ROUTINGKEY_DD);
    }

    @Bean
    public Binding bindingDead() {

        return BindingBuilder.bind(deadQueue()).to(ExchangeDead()).with(RabbitConfig.ROUTINGKEY_DEAD);
    }
}