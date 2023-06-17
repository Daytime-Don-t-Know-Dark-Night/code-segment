package kafka.basic;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerTest {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.105:9092");

        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);

        String topic = "test";
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "testGroupId");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        try {
            consumer.subscribe(Collections.singletonList(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("topic: %s, partition: %s, offset: %s, key: %s, value: %s%n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally close !!");
            consumer.commitAsync();
        }


    }

}
