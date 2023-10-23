package com.mail.order;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.plugin.com.AmbientProperty;

@SpringBootTest
class MailOrderApplicationTests {

	/**
	 * 	1. 如何创建 Exchange、Queue、Binding、
	 * 		1. AmqpAdmin管理以上 amqpAdmin.declare(Exchange)
	 * 	2. 如何收发消息
	 * 		rabbitTemplate.convertAndSend 发送消息
	 * 		发送 Java对象时，默认会将序列化后的结果发送出去。所以要求被发送的对象必须实现序列化接口 Serializable
	 * 		如果要修改消息转化的方法，则在配置类中配置 MessageConvert; 如 Jackson2JsonMessageConverter 转成 json 格式
	 *
	 * 		@RabbitListenner( queues = {"queue-name1", "queue-name2", ...}) 监听消息（在 Service中的方法上标注）
	 * 		@RabbitHandler 实现方法重载效果
	 */

	@Autowired
	AmqpAdmin amqpAdmin;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Test
	public void sendMessage() {
		rabbitTemplate.convertAndSend("exchange-name", "route-key", "message");
	}

	@Test
	void creatExchange() {
		// 全参构造 DirectExchange(String name(exchange-name), boolean durable(是否持久化), boolean autoDelete(是否自动删除), Map<String, Object> arguments(其余参数))
		// 也可构建其它类型的交换器
		DirectExchange directExchange = new DirectExchange("exchange-name", true, false);
		// new FanoutExchange();
		// new TopicExchange();
		amqpAdmin.declareExchange(directExchange);
	}

	@Test
	void creatQueue() {
		// Queue(String name, boolean durable, boolean exclusive(是否排他，只给已连接的交换机使用), boolean autoDelete,
		//			@Nullable Map<String, Object> arguments)
		amqpAdmin.declareQueue(new Queue("Queue-name", true, false, false));
	}

	@Test
	void creatBinding() {
		// Binding(String destination(目的地), DestinationType destinationType(目的地类型), String exchange(交换机), String routingKey(路由键),
		//			@Nullable Map<String, Object> arguments(其余参数))
		amqpAdmin.declareBinding(new Binding("Queue-name", Binding.DestinationType.QUEUE, "exchange-name", "route-key", null));
	}

}
