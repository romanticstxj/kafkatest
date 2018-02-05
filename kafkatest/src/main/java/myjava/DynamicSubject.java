package myjava;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicSubject implements InvocationHandler{
	
	private Object target;
	
	public DynamicSubject(Object target) {
		// TODO Auto-generated constructor stu
		this.target = target;
	}
	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before subject");
		method.invoke(target, args);
		System.out.println("after subject");
		return null;
	}
	
}
