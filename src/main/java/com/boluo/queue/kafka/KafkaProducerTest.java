package com.boluo.queue.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerTest.class);

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

			// 1.使用同步等待的方式发送消息
			Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<String, String>("test", null, "" + i));
			future.get();
			System.out.println("第" + i + "条消息, 写入成功! ");

			// 2.使用异步回调的方式发送消息
			ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test", null, "" + i);
			kafkaProducer.send(producerRecord, new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					// 1.判断消息是否发送成功,
					if (exception == null) {
						// 发送成功
						String topic = metadata.topic();
						int partition = metadata.partition();
						long offset = metadata.offset();
						logger.info("topic: {}, partition: {}, offset: {}", topic, partition, offset);
					} else {
						// 发送出现错误
						logger.info("出现异常, 发送失败! {}", exception.getMessage());
					}
				}
			});
		}

		kafkaProducer.close();
	}
}
