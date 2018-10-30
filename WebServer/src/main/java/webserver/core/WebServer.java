package webserver.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器主类
 * @author soft01
 *
 */
public class WebServer {
	private ServerSocket server;
	
	private ExecutorService threadPool;
	/**
	 * 初始化WebServer
	 */
	public WebServer(){
		try {
			System.out.println("正在启动服务端...");
			server = new ServerSocket(8088);
			threadPool = Executors.newFixedThreadPool(50);
			System.out.println("服务端启动完毕!");
		} catch (Exception e) {
			
		}
	}
	/**
	 * 服务端启动方法
	 * 
	 * URL 统一资源定位
	 * DNS 域名解读器
	 */
	public void start(){
		try {
			while(true){
				System.out.println("等待客户端连接...");
				Socket socket = server.accept();
				System.out.println("一个客户端连接了...");
				//启动线程处理该客户端请求
				ClientHandler handler = new ClientHandler(socket);
//				Thread t = new Thread(handler);
//				t.start();
				//将任务交给线程池
				threadPool.execute(handler);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		WebServer server = new WebServer();
		server.start();
	}
}
