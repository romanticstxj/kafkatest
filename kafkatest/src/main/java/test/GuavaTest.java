package test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;

public class GuavaTest {

	public static void main(String[] args) {
		LoadingCache<String, Object> caches = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                    	System.out.println(Thread.currentThread() + " begin to generate");
                        return generateValueByKey(key);
                    }

					private Object generateValueByKey(String key) {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Thread.currentThread() + " hello " + key;
					}

					
					
                });
		for(int i=0;i<5;i++){
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						System.out.println(caches.get("key-zorro"));
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}}).start();
		}
	}

}
