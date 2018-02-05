package michael.kafkatest;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerExecutor2 implements Runnable{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Thread(new ConsumerExecutor2()).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Properties props = new Properties();
	     props.put("bootstrap.servers", "172.16.25.27:9092,172.16.25.28:9092,172.16.25.29:9092");
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList("test_hdfs", "topic_test"));
	     consumer.close();
//	     while (true) {
//	         ConsumerRecords<String, String> records = consumer.poll(100);
//	         for (ConsumerRecord<String, String> record : records){
//	        	 System.out.printf("partition = %d, offset = %d, key = %s, value = %s", 
//	        			 record.partition(), record.offset(), record.key(), record.value());
//	        	 System.out.println();
//	         }
//	         
//	     }
	}

}
