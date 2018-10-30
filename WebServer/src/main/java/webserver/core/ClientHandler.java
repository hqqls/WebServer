package webserver.core;

import webserver.http.EmptyRequestException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.servlet.HttpServlet;

import java.io.File;
import java.net.Socket;
/**
 * 用来处理客户端请求+
 * @author soft01
 *
 */
public class ClientHandler implements Runnable{
	private Socket socket;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	public void run(){
		try {
			//1.解析请求
			HttpRequest request = new HttpRequest(socket);
			
			//创建响应对象
			HttpResponse response = new HttpResponse(socket);
			
			//2.处理请求
			//2.1 获取请求的资源路径
			String url = request.getRequestURI();
			System.out.println("URL:"+url);
			//2.2判断是否为请求业务
			String  servletName =ServerContext.getServletName(url);
			//若Servlet名字不为null。说明对应该请求的是业务
			if(servletName!=null){
				System.out.println("加载："+servletName);
				//加载该Servlet
				Class cls =Class.forName(servletName);	
				
				HttpServlet servlet = (HttpServlet)cls.newInstance();
				
				System.out.println("调用"+servletName+"的servlet开始");
				
				servlet.service(request, response);
			}
			else{
				//2.3 对应的从webapps目录中找到该资源
				File file = new File("webapps"+url);
				
				//2.4 判断用户请求的资源是否真实存在
				if(file.exists()){
					System.out.println("资源已找到!");
					
					//将资源设置到响应对象上
					response.setEntity(file);				
					
				}else{
					System.out.println("资源不存在!");
					//响应404页面
					//1.设置状态代码为404
					response.setStatusCode(404);
					//2.设置错误页面
					File notFoundPage = new File("webapps/root/404.html");
					response.setEntity(notFoundPage);
				}
			}
			
			//响应客户端
			response.flush();
		}catch(EmptyRequestException e){
			System.out.println("空请求!");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//处理断开连接后的操作
			try {
				socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
