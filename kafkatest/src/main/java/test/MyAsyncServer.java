package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class A{
	
}

public class MyAsyncServer {
	
	public static void main(String[] args) throws IOException {
		int port = 10086;
		ServerSocket ss = new ServerSocket(port);
		System.out.println(ss.toString() + "等待与客户端建立连接...");  
		A task = new A();
		while(true){
			Socket socket = ss.accept();
			
			new Thread(new Task(socket)).start();
		}
		
		
	}
	
	static class Task implements Runnable{
		private Socket socket;

		public Task(Socket socket){
			this.socket = socket;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				handleSocket(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void handleSocket(Socket socket) throws IOException {
			// TODO Auto-generated method stub
			BufferedReader is= null;
			PrintWriter os = null;
			try{
				is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				System.out.println(socket);
				System.out.println(is);
				//由Socket对象得到输入流，并构造相应的BufferedReader对象
				os=new PrintWriter(socket.getOutputStream());
				//由Socket对象得到输出流，并构造PrintWriter对象
//				BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
				//由系统标准输入设备构造BufferedReader对象
//				System.out.println("Client:"+is.readLine());
				//在标准输出上打印从客户端读入的字符串
//				line=sin.readLine();
				//从标准输入读入一字符串
//				Thread.sleep(10000000);
				int i=0;
				while(i<10){
					//刷新输出流，使Client马上收到该字符串
//					System.out.println("Server:"+line);
					//在系统标准输出上打印读入的字符串
//					System.out.println("Client:"+is.readLine());
					//如果该字符串为 "bye"，则停止循环
					os.println("hello client" + i + ":"  + socket);
					//向客户端输出该字符串
					os.flush();
					System.out.println("hello client" + i + ":"  + socket);
					i++;
					Thread.sleep(1000);
					//从Client读入一字符串，并打印到标准输出上
//					line=sin.readLine();
					//从系统标准输入读入一字符串
				} //继续循环
//				os.close(); //关闭Socket输出流
				
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				is.close(); //关闭Socket输入流
				os.close();
				socket.close(); //关闭Socket
			}
		}
		
	}
}
