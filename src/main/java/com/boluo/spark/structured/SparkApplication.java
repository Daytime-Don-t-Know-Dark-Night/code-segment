package com.boluo.spark.structured;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author chao
 * @datetime 2025-01-22 21:29
 * @description
 */
public class SparkApplication {

    public static void main(String[] args) {

    }


    static void getPartitionsForTopic(String topicName, Properties properties) {
        Consumer<Long, String> consumer = new KafkaConsumer<>(properties);

        // 获取主题的分区信息
        List<PartitionInfo> partitions = consumer.partitionsFor(topicName)
                .stream()
                .sorted((part1, part2) -> part1.partition() - part2.partition())
                .collect(Collectors.toList());
        System.out.printf("Topic %s has %d partitions.%n", topicName, partitions.size());

        Map<TopicPartition, Long> beginningOffsets = consumer.beginningOffsets(partitions.stream()
                .map(p -> new TopicPartition(topicName, p.partition()))
                .collect(Collectors.toList()));

        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(partitions.stream()
                .map(p -> new TopicPartition(topicName, p.partition()))
                .collect(Collectors.toList()));

        for (PartitionInfo partition : partitions) {
            TopicPartition topicPartition = new TopicPartition(topicName, partition.partition());
            Long beginningOffset = beginningOffsets.get(topicPartition);
            long endOffset = endOffsets.get(topicPartition);
            System.out.printf("Partition %d: beginning offset = %d, end offset = %d%n", partition.partition(), beginningOffset, endOffset);
        }
        consumer.close();
    }


}