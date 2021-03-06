package michael.kafkatest.controller;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class FirstControllerTest {
	
	private final static int SIZE = 100;

	public static void main(String[] args) {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		
		final CountDownLatch latch = new CountDownLatch(SIZE);
		final HttpGet request = new HttpGet("http://localhost:8080/kafkatest/first/detail");
		
		System.out.println(" caller thread is: " + Thread.currentThread().getId());
		for(int i=0; i<SIZE; i++){
			httpclient.execute(request, new FutureCallback<HttpResponse>() {
				
				@Override
				public void completed(final HttpResponse response) {
					latch.countDown();
					System.out.println(" callback thread id is : " + Thread.currentThread().getId());
					System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
					try {
						String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                    System.out.println(" response content is : " + content);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void failed(final Exception ex) {
					latch.countDown();
					System.out.println(request.getRequestLine() + "->" + ex);
					System.out.println(" callback thread id is : " + Thread.currentThread().getId());
				}
				
				@Override
				public void cancelled() {
					latch.countDown();
					System.out.println(request.getRequestLine() + " cancelled");
					System.out.println(" callback thread id is : " + Thread.currentThread().getId());
				}
			});
		}
		
		try {
			System.out.println("latch await");
            latch.await();
            System.out.println("latch await completed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            httpclient.close();
        } catch (IOException ignore) {

        }
	}
	
	@Test
	public void testRandom(){
		Random rand = new Random();
		for(int i=0; i< 100; i++){
			System.out.println(rand.nextInt(10));
		}
	}
}
