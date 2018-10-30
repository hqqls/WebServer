package webserver.servlet;


import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.RandomAccessFile;

/**
 * 处理用户登录业务
 * @author soft01
 *
 */
public class LoginServlet extends HttpServlet{
	
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("LoginServlet:开始处理用户登录");
		/*
		 * 1:获取表单数据(用户页面输入的注册信息)
		 * 2:讲数据写入user.dat文件
		 * 3:设置response响应注册成功页面
		 */
		//1
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//2
		
		try{
				RandomAccessFile raf = new RandomAccessFile("user.dat","r");

			boolean flag = false;
			for(int i=0;i<raf.length()/100;i++){
	 			raf.seek(i*100);
	 			byte[] data = new byte[32];
	 			raf.read(data);
	 			String name = new String(data,"UTF-8").trim();
	 			if(name.equals(username)){
	 				raf.read(data);
	 				String pwd = new String(data,"UTF-8").trim();
	 				if(pwd.equals(password)){
	 					flag = true;
	 				}
		 			break;
	 				}				
	 		}
		    if(flag){
		    	System.out.println("登录成功!");
		    	/*
		    	 * 需要注意，内部跳转页面时使用的相对路径是服务端这边
		    	 * 指定的相对路径
		    	 * 而重定向是将路径发送给客户端，让其根据该地址发送请求，
		    	 * 所以制定的相对路径是相对浏览器上次请求的路径。
		    	 * 这里要注意区分
		    	 */
		    	forward("/myweb/login_success.html",request,response);
		        response.sendRedirect("login_success.html");
		    }else{
		    	System.out.println("登录失败!");
		       // forward("/myweb/login_fail.html",request,response);
		    	response.sendRedirect("login_fail.html");
		    }
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
}
