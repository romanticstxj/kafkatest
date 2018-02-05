package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest implements Runnable{
	
	private int i;
	
	public void testThread(){
		System.out.println(Thread.currentThread());
		List list = new ArrayList();
		testThread2(list);
	}
	
	public void testThread2(List list){
		System.out.println("testThread2:" + Thread.currentThread());
//		System.out.println(Thread.currentThread());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService es = Executors.newCachedThreadPool();
		for(int i=0; i<10; i++){
			es.submit(new ThreadTest());
			
		}
	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		testThread();
	}

}
