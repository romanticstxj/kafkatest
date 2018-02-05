package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyClient {

	public static void main(String[] args) throws IOException {
//		ExecutorService es = Executors.newCachedThreadPool();
//		for(int i=0; i<3; i++){
//			es.submit(new Runnable(){
//				
//				@Override
//				public void run() {
//					try {
//						send();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}});
//			
//		}
//		for(int i=0; i<65535; i++){
//			char content=(char)i;
//			System.out.println(content);
//		}
		send();
	}
	
	public static void send() throws IOException{
		PrintWriter os=null;
		Socket socket=null;
		BufferedReader is = null;
		try{
			socket = new Socket("127.0.0.1",10086);
			System.out.println(socket);
			//向本机的4700端口发出客户请求
//			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			//由系统标准输入设备构造BufferedReader对象
			os=new PrintWriter(socket.getOutputStream());
			//由Socket对象得到输出流，并构造PrintWriter对象
			is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//由Socket对象得到输入流，并构造相应的BufferedReader对象
//			int i=0;
//			while(true){
////				os.println(Thread.currentThread() + "//" + socket.toString() + " no." + i);
////				os.flush();
//				String readLine = is.readLine();
//				System.out.println(socket + ":" + readLine);
////				System.out.println(Thread.currentThread() + "//" + socket.toString() + " no." + i);
//				i++;
//				Thread.sleep(1000);
//			}
			int size=0;
            while((size=is.read())!=-1){
                char content=(char)size;
                System.out.print(content);
            }
//			readline=sin.readLine(); //从系统标准输入读入一字符串
//			while(!readline.equals("bye")){
//				//若从标准输入读入的字符串为 "bye"则停止循环
//				os.println(readline);
//				//将从系统标准输入读入的字符串输出到Server
//				os.flush();
//				//刷新输出流，使Server马上收到该字符串
//				System.out.println("Client:"+readline);
//				//在系统标准输出上打印读入的字符串
////				System.out.println("Server:"+is.readLine());
//				//从Server读入一字符串，并打印到标准输出上
////				readline=sin.readLine(); //从系统标准输入读入一字符串
//			} //继续循环
			
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			os.close(); //关闭Socket输出流
			is.close(); //关闭Socket输入流
			socket.close(); //关闭Socket
		}
	}

}
