package com.boluo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Kafka的生产者程序
 * 会将消息创建出来, 并发送到Kafka集群中
 * <p>
 * 1.创建用于连接Kafka的Properties配置
 * 2.创建一个生产者对象KafkaProducer
 * 3.调用send发送1-100消息到指定Topic test，并获取返回值Future，该对象封装了返回值
 * 4.再调用一个Future.get()方法等待响应
 * 5.关闭生产者
 *
 * @Author dingc
 * @Date 2022/2/2 23:37
 */
public class KafkaProducerTest {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		// 1.创建用于连接Kafka的Properties配置
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.88.100:9092");
		props.put("acks", "all");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		// 2.创建一个生产者对象KafkaProducer
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

		// 3.发送1-100的消息到指定的topic中
		for (int i = 0; i < 100; i++) {
			Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<String, String>("test", null, "" + i));
			future.get();
			System.out.println("第" + i + "条消息, 写入成功! ");
		}

		kafkaProducer.close();
	}
}
