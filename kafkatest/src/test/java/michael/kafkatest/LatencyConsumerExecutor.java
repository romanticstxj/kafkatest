package michael.kafkatest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

public class LatencyConsumerExecutor implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Properties props = new Properties();
	     props.put("bootstrap.servers", "172.16.25.27:9092,172.16.25.28:9092,172.16.25.29:9092");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "false");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test_hdfs", "topic_test"));
	     final int minBatchSize = 5;
	     List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
//	     while (true) {
//	         ConsumerRecords<String, String> records = consumer.poll(100);
//	         for (ConsumerRecord<String, String> record : records) {
////	        	 System.out.println(record);
//	             buffer.add(record);
//	         }
//	         if (buffer.size() >= minBatchSize) {
//	             insertIntoDb(buffer);
////	             consumer.commitSync();
//	             buffer.clear();
//	         }
//	     }
	     
	     try {
	         while(true) {
	             ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
	             for (TopicPartition partition : records.partitions()) {
	                 List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
	                 for (ConsumerRecord<String, String> record : partitionRecords) {
	                     System.out.println(record.partition() + ": " + record.offset() + ": " + record.value());
	                 }
	                 long lastOffset = partitionRecords.get(0).offset();
	                 System.out.println("commit partition: " + partition + ", lastOffset: " + lastOffset);
	                 consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset)));
	                 consumer.seek(partition, lastOffset);
	             }
	         }
	     } finally {
	       consumer.close();
	     }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Thread(new LatencyConsumerExecutor()).start();
	}
	
	private void insertIntoDb(List<ConsumerRecord<String, String>> buffer){
		System.out.println("insert into db" + buffer);
	}

}
