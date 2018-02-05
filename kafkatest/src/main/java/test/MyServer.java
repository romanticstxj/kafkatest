package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		try{
			ServerSocket server=null;
			try{
				server=new ServerSocket(10086);
				System.out.println(server);
				//创建一个ServerSocket在端口4700监听客户请求
			}catch(Exception e) {
				System.out.println("can not listen to:"+e);
				//出错，打印出错信息
			}
			Socket socket=null;
			try{
				socket=server.accept();
				//使用accept()阻塞等待客户请求，有客户
				//请求到来则产生一个Socket对象，并继续执行
			}catch(Exception e) {
				System.out.println("Error."+e);
				//出错，打印出错信息
			}
			String line = "";
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(socket);
			System.out.println(is);
			//由Socket对象得到输入流，并构造相应的BufferedReader对象
//			PrintWriter os=new PrintWriter(socket.getOutputStream());
			//由Socket对象得到输出流，并构造PrintWriter对象
//			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			//由系统标准输入设备构造BufferedReader对象
			System.out.println("Client:"+is.readLine());
			//在标准输出上打印从客户端读入的字符串
//			line=sin.readLine();
			//从标准输入读入一字符串
			while(line.equals("")){
				//如果该字符串为 "bye"，则停止循环
//				os.println(line);
				//向客户端输出该字符串
//				os.flush();
				//刷新输出流，使Client马上收到该字符串
//				System.out.println("Server:"+line);
				//在系统标准输出上打印读入的字符串
				System.out.println("Client:"+is.readLine());
				//从Client读入一字符串，并打印到标准输出上
//				line=sin.readLine();
				//从系统标准输入读入一字符串
			} //继续循环
//			os.close(); //关闭Socket输出流
			is.close(); //关闭Socket输入流
			socket.close(); //关闭Socket
			server.close(); //关闭ServerSocket
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void send(String send) throws IOException {
		String rtn = null;
		BufferedWriter writer = null;
		OutputStreamWriter ow = null;
		OutputStream os = null;
		try {
			ServerSocket server = new ServerSocket(10086);
			String readLine = "";
			while (true) {
				// System.out.println(server);
				Socket socket = server.accept();
				System.out.println(socket);

				BufferedReader bd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while ((readLine = bd.readLine()) != null && !readLine.isEmpty()) {
					System.out.println(readLine);
				}
				// os = socket.getOutputStream();
				// ow = new OutputStreamWriter(os);
				// writer = new BufferedWriter(ow);
				// char [] sendChar = send.toCharArray();
				// ArrayList<Integer> list = new ArrayList<Integer>();
				// for(char ch:sendChar){
				// list.add((int)ch);
				// }
				// //进行加密操作
				//// list = encry(list);
				// Iterator<Integer> it = list.iterator();
				// while(it.hasNext()){
				// writer.write(it.next());
				// }
				// writer.flush();
				// rtn = "发送成功！";
			}

		} finally {
			// 注意：直接关闭流将会导致socket关闭，只能通过shutdownOutput/input的方式关闭流
			// 另外，流关闭之后，相当于关闭底层的连接，除非新new个socket，否则和客户端的连接相当于断开
			// if(writer!=null){
			// writer.close();
			// }
			// if(ow!=null){
			// ow.close();
			// }
			// if(os!=null){
			// os.close();
			// }
			// socket.shutdownOutput();流关闭之后，相当于关闭底层的连接，除非新<br>new个socket，否则和客户端的连接相当于断开
		}
		// return rtn;
	}

}
