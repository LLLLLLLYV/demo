package com.example.demo.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * kafka的分区器
 * 消息通过send()方法发送到broker的过程中，可能会经历拦截器（非必须）、序列化器（必须）、分区器（send方法可以指定分区）的一系列作用后才能发往broker。
 * 如果消息在ProducerRecord中指定了partition字段，那么就不需要分区器，直接发往指定的partition分区中。
 * 如果ProducerRecord 中没有指定partition字段，那么就需要分区器，根据key字段的值计算分区。
 * kafka默认设有分区器org.apache.kafka.clients.producer.internals.DefaultPartitioner。
 *
 * 通过ProducerConfig.PARTITIONER_CLASS_CONFIG 来指定自定义分区器
 *
 */
public class DemoPartitioner implements Partitioner {
    private final ConcurrentMap<String, AtomicInteger> topicCounterMap = new ConcurrentHashMap();
    /**
     * 计算分区号
     * @param topic 主题
     * @param key 键
     * @param keyBytes 序列化后的键
     * @param value 值
     * @param valueBytes 序列化后的值
     * @param cluster 集群元素信息
     * @return 返回分区号
     */

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //根据topic获取分区信息
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();

        if (keyBytes == null) {
            int nextValue = this.nextValue(topic);
            List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
            if (availablePartitions.size() > 0) {
                int part = Utils.toPositive(nextValue) % availablePartitions.size();
                return ((PartitionInfo)availablePartitions.get(part)).partition();
            } else {
                return Utils.toPositive(nextValue) % numPartitions;
            }
        } else {
            return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
        }
    }
    private int nextValue(String topic) {
        AtomicInteger counter = (AtomicInteger)this.topicCounterMap.get(topic);
        if (null == counter) {
            counter = new AtomicInteger(ThreadLocalRandom.current().nextInt());
            AtomicInteger currentCounter = (AtomicInteger)this.topicCounterMap.putIfAbsent(topic, counter);
            if (currentCounter != null) {
                counter = currentCounter;
            }
        }

        return counter.getAndIncrement();
    }
    @Override
    public void close() {

    }

    /**
     * 用来获取配置信息以及初始化数据
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {

    }
}
