package com.boluo.queue.kafka;

import com.google.common.collect.ImmutableList;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;

/**
 * 1.创建Kafka消费者配置
 * 2.创建Kafka消费者
 * 3.订阅要消费的主题
 * 4.使用一个while循环，不断从Kafka的topic中拉取消息
 * 5.将将记录（record）的offset、key、value都打印出来
 *
 * @Author dingc
 * @Date 2022/2/4 16:17
 */
public class KafkaConsumerTest {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerTest.class);

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "node1.itcast.cn:9092");
		// 消费者组(可以使用消费者组将若干个消费者组织到一起), 共同消费kafka中topic的数据
		// 每一个消费者需要指定一个消费者组, 如果消费者的组名是一样的, 表示这几个消费者是一个组中的
		props.setProperty("group.id", "test");
		// 自动提交offset
		props.setProperty("enable.auto.commit", "true");
		// 自动提交offset的时间间隔
		props.setProperty("auto.commit.interval.ms", "1000");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		// 2.创建kafka消费者
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);

		// 3.订阅要消费的主题, 指定消费者从哪个topic中拉取数据
		kafkaConsumer.subscribe(ImmutableList.of("test"));

		// 4.不断地从kafka中拉取数据
		while (true) {
			ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(5));
			for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
				String topic = consumerRecord.topic();
				long offset = consumerRecord.offset();
				String key = consumerRecord.key();
				String value = consumerRecord.value();
				logger.info("topic: {}, offset: {}, key: {}, value: {}", topic, offset, key, value);
			}
		}
	}

}
