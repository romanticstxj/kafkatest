package main;

import com.madhouse.kafkaclient.producer.KafkaProducer;

public class ResourceManager {
	
	private KafkaProducer kafkaProducer;
	
	public KafkaProducer getKafkaProducer() {
		return kafkaProducer;
	}

	private static final ResourceManager instance = new ResourceManager();
	
	public static ResourceManager getInstance(){
		return instance;
	}
	
	public boolean init(){
		this.kafkaProducer = new KafkaProducer("172.16.25.27:9092,172.16.25.28:9092,172.16.25.29:9092", 8, null);
		return kafkaProducer.start(new LogUtils());
	}
}
