package webserver.servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.RandomAccessFile;
import java.util.Arrays;

public class UpdateUserServlet extends HttpServlet {
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("UpdateUserServlet:开始处理用户修改");
		/*
		 * 1:获取表单数据(用户页面输入的注册信息)
		 * 2:讲数据写入user.dat文件
		 * 3:设置response响应注册成功页面
		 */
		//1
		String username = request.getParameter("username");
		String newpassword =request.getParameter("newpassword");
		String newnickname = request.getParameter("newnickname");
		int newage = Integer.parseInt(request.getParameter("newage"));
		try{
			RandomAccessFile raf = new RandomAccessFile("user.dat","rw");
			for(int i=0;i<raf.length()/100;i++){
	 			raf.seek(i*100);
	 			byte[] data = new byte[32];
	 			raf.read(data);
	 			String name = new String(data,"UTF-8").trim();
	 			if(name.equals(username)){
//	 				raf.read(data);
//	 				String pwd = new String(data,"UTF-8").trim();
//	 				if(pwd.equals(password)){
//	 					//写密码
	 					raf.seek(i*100+32);
	 					data = newpassword.getBytes("UTF-8");
	 					data = Arrays.copyOf(data, 32);
	 					raf.write(data);
	 					//写昵称
	 					data = newnickname.getBytes("UTF-8");
	 					data = Arrays.copyOf(data, 32);
	 					raf.write(data);
	 					//写年龄
	 					raf.writeInt(newage);
	 					System.out.println("修改成功!");
	 			    	forward("/myweb/update_success.html",request,response);
	 					
	 				}else{
	 					System.out.println("修改失败!");
	 			    	forward("/myweb/update_fail.html",request,response);
	 				}
		 			break;
			}
	 	}catch(Exception e){
			e.printStackTrace();
		}	
	
}

}
