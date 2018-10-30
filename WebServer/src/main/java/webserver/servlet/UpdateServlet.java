package webserver.servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * 用来修改用户密码
 * 
 * @author soft01
 *
 */
public class UpdateServlet extends HttpServlet{
	
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("UpdateServlet:开始处理用户修改密码");
		/*
		 * 1:获取表单数据(用户页面输入的注册信息)
		 * 2:讲数据写入user.dat文件
		 * 3:设置response响应注册成功页面
		 */
		//1
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String newPassword =request.getParameter("newPassword");
		System.out.println(username);
		System.out.println(password);
		System.out.println(newPassword);
		//2
		
		try{RandomAccessFile raf = new RandomAccessFile("user.dat","rw");
			int  flag = 0;
			for(int i=0;i<raf.length()/100;i++){
	 			raf.seek(i*100);
	 			byte[] data = new byte[32];
	 			raf.read(data);
	 			String name = new String(data,"UTF-8").trim();
	 			if(name.equals(username)){
	 				raf.read(data);
	 				String pwd = new String(data,"UTF-8").trim();
	 				if(pwd.equals(password)){
	 					//写密码
	 					raf.seek(i*100+32);
	 					data = newPassword.getBytes("UTF-8");
	 					data = Arrays.copyOf(data, 32);
	 					raf.write(data);
	 					flag = 1;
	 				}else{
	 					flag=2;
	 				}
		 			break;
	 				}	
	 			else{
	 				flag =3;
	 				
	 			}
	 		}
			switch(flag){
			case  1:
				System.out.println("修改成功!");
		    	forward("/myweb/update_success.html",request,response);
				break;
            case  2:
            	System.out.println("修改失败!");
		    	forward("/myweb/update_fail.html",request,response);
				break;
             case  3:
            	 System.out.println("无此用户");
 		    	forward("/myweb/no_user.html",request,response);
	            break;
			}
		    
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
}
