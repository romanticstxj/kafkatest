package michael.kafkatest;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

public class KafkaConsumerRunner implements Runnable {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    
    public static void main(String[] args) {
		new Thread(new KafkaConsumerRunner()).start();
	}
    
    private KafkaConsumer consumer;
    
    {
    	
    	Properties props = new Properties();
  	     props.put("bootstrap.servers", "172.16.25.27:9092,172.16.25.28:9092,172.16.25.29:9092");
  	     props.put("group.id", "test");
  	     props.put("enable.auto.commit", "true");
  	     props.put("auto.commit.interval.ms", "1000");
  	     props.put("session.timeout.ms", "30000");
  	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
  	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
  	   consumer = new KafkaConsumer<>(props);
    }

    public void run() {
        try {
   	     consumer.subscribe(Arrays.asList("test_hdfs", "topic_test"));
   	     while (!closed.get()) {
   	         ConsumerRecords<String, String> records = consumer.poll(100);
   	         for (ConsumerRecord<String, String> record : records){
   	        	 System.out.printf("partition = %d, offset = %d, key = %s, value = %s", 
   	        			 record.partition(), record.offset(), record.key(), record.value());
   	        	 System.out.println();
   	         }
   	         
   	     }
        } catch (WakeupException e) {
            // Ignore exception if closing
        	System.out.println("wakeup ex");
            if (!closed.get()) throw e;
        } finally {
            consumer.close();
        }
    }

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
//        consumer.wakeup();
    }
}