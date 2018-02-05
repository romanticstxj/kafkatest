package michael.kafkatest;

public class Consumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int consumerNum = 2;
		for(int i=0; i<consumerNum; i++){
			new Thread(new ConsumerExecutor()).start();
		}
	}

}
