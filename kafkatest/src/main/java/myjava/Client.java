package myjava;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Subject realSubject = new RealSubject();
		InvocationHandler dynaSubject = new DynamicSubject(realSubject);
		
		Proxy proxy = (Proxy) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), 
				realSubject.getClass().getInterfaces(), dynaSubject);
		
		InvocationHandler ih = Proxy.getInvocationHandler(proxy);
		
		
		System.out.println(proxy instanceof Proxy);
		System.out.println(proxy.getClass().toString());
		
		Field[] fields = proxy.getClass().getDeclaredFields();
		for(Field field: fields){
			System.out.println(field.getName());
		}
		
		Method[] methods = proxy.getClass().getDeclaredMethods();
		for(Method method: methods){
			System.out.println(method.getName());
		}
		
		System.out.println("proxy's father is " + proxy.getClass().getSuperclass());
		
		
		
	}
	

}
