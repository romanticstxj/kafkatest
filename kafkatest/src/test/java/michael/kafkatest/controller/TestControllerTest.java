package michael.kafkatest.controller;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestControllerTest {
	
	private static final int SIZE = 10;

	@Test
	public void test() throws InterruptedException{
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		
		final HttpGet request = new HttpGet("http://localhost:8080/services/media/list");
		
		System.out.println(" caller thread is: " + Thread.currentThread());
//		ExecutorService es = Executors.newCachedThreadPool();
		for(int i=0; i<SIZE; i++){
			httpclient.execute(request, new FutureCallback<HttpResponse>() {
				
				@Override
				public void completed(final HttpResponse response) {
					System.out.println(" callback thread id is : " + Thread.currentThread());
					System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
					try {
						String content = EntityUtils.toString(response.getEntity(), "UTF-8");
//                    System.out.println(" response content is : " + content);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void failed(final Exception ex) {
					System.out.println(request.getRequestLine() + "->" + ex);
					System.out.println(" callback thread id is : " + Thread.currentThread().getId());
				}
				
				@Override
				public void cancelled() {
					System.out.println(request.getRequestLine() + " cancelled");
					System.out.println(" callback thread id is : " + Thread.currentThread().getId());
				}
			});
		}
		
		Thread.sleep(10000);
        try {
            httpclient.close();
        } catch (IOException ignore) {

        }
	}
	
	public static void main(String[] argv) {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		
		final CountDownLatch latch = new CountDownLatch(1);
		final int a=0;
		final HttpGet request = new HttpGet("http://localhost:8080/services/media/list");
		
		System.out.println(" caller thread is: " + Thread.currentThread().getId());
//		ExecutorService es = Executors.newCachedThreadPool();
//		for(int i=0; i<SIZE; i++){
			httpclient.execute(request, new FutureCallback<HttpResponse>() {
				
				@Override
				public void completed(final HttpResponse response) {
					latch.countDown();
					System.out.println(" callback thread id is : " + Thread.currentThread().getId());
					System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
					try {
						String content = EntityUtils.toString(response.getEntity(), "UTF-8");
//                    System.out.println(" response content is : " + content);
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
//		}
		
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
}
