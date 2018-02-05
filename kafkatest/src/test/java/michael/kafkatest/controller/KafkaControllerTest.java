package michael.kafkatest.controller;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaControllerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	     Properties props = new Properties();
		 props.put("bootstrap.servers", "172.16.25.27:9092,172.16.25.28:9092,172.16.25.29:9092");
		 props.put("acks", "1");
		 props.put("retries", 3);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 
        
        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 100; i++){
        	String idAndName = new StringBuilder(Integer.toString(i)).append(" ").append("Michael").append(Integer.toString(i)).toString();
        	producer.send(new ProducerRecord<String, String>("topic_test", Integer.toString(i), idAndName));
        }

        producer.close();
	}

}
