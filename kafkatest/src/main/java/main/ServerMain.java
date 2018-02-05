package main;

import java.util.Collections;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import com.madhouse.util.httpserver.HttpServer;
import com.madhouse.util.httpserver.ServletHandler;

public class ServerMain {

	public static void main(String[] args) {
		ResourceManager.getInstance().init();
		
		ServletHandler handler = new ServletHandler(null);
		handler.addHandler("/adcall/test", new MyServlet());
		ResourceHandler resourceHandler = new ResourceHandler();
		  resourceHandler.setResourceBase("D:/");
		  resourceHandler.setDirectoriesListed(true);
		HttpServer httpServer = new HttpServer(5, 10, 1000, new Handler[]{handler, resourceHandler});
		
		
		if(httpServer.start(8082)){
			try {
				httpServer.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
