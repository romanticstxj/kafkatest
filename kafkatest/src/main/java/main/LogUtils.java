package main;

import com.alibaba.fastjson.JSON;
import com.madhouse.kafkaclient.producer.KafkaProducer;
import com.madhouse.kafkaclient.util.KafkaCallback;
import com.madhouse.kafkaclient.util.KafkaMessage;

public class LogUtils extends KafkaCallback{
	
	private static final LogUtils logger = new LogUtils();
	
	public static LogUtils getInstance(){
		return logger;
	}

	@Override
	public void onCompletion(KafkaMessage message, Exception e) {
		System.out.println("kafka finished");
		super.onCompletion(message, e);
	}
	
	public void writeMyLog(KafkaProducer kafkaProducer, String message) {
        try {
            sendMessage(kafkaProducer, message.getBytes() , "topic_test");
        } catch (Exception e) {
            System.out.println(String.format("%s-%s", e.toString(),JSON.toJSONString(message)));
        }
    }
	
	private void sendMessage(KafkaProducer kafkaProducer, byte[] message,String topic) {
        kafkaProducer.sendMessage(topic, message);
    }

	
}
