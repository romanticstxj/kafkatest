package michael.kafkatest.controller;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/first")
public class FirstController {
	
	@RequestMapping("/detail")
    public Object getRandom(){
        Random rand = new Random(47);
        int randInt = rand.nextInt();
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.26.27:9092,172.16.26.28:9092,172.16.26.29:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("acks", "1");
        props.put("retries", 3);
        
        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<String, String>("topic_test", Integer.toString(i), Integer.toString(i)));

        producer.close();
        
		return randInt;
    }
}
