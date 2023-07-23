package kafka.asyn;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * @author chao
 * @datetime 2023-06-17 13:27
 * @description kafka 异步发送消息
 */
public class KafkaAsynProducerTest {

    // https://blog.csdn.net/u011294519/article/details/104723784
    private static KafkaProducer<String, String> producer = null;

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.105:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        String topic = "asyn-test";

        producer = new KafkaProducer<>(props);
        try {
            for (int i = 0; i < 5; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, "t-key", "Message: " + i);
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (exception != null) {
                            exception.printStackTrace();
                        }
                        if (metadata != null) {
                            System.out.printf("topic: %s, partition: %s, offset: %s%n", metadata.topic(), metadata.partition(), metadata.offset());
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }

}
