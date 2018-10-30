package webserver.servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.RandomAccessFile;

public class ShowAllUserServlet  extends HttpServlet{
	
	public void service(HttpRequest request, HttpResponse response) {

		try{
			RandomAccessFile raf =new RandomAccessFile("user.dat","r");
			StringBuilder builder =new StringBuilder();
			builder.append("<html>");
			builder.append("<head>");
			builder.append("<title>用户列表</title>");
			builder.append("<meta charset='UTF-8'>");
			builder.append("</head>");
			builder.append("<body>");
			builder.append("<center>");
			builder.append("<h1>用户列表</h1>");
			builder.append("<table border ='4'>");
			builder.append("<tr><td>用户名</td><td>密码</td><td>昵称</td><td>年龄</td><td>操作</td></tr>");
			for(int i =0;i<raf.length()/100;i++){
			byte[] data =new byte[32];
			raf.read(data);
			String username =new String(data,"UTF-8").trim();
			
			raf.read(data);
			String password =new String(data,"UTF-8").trim();
			
			raf.read(data);
			String nickname =new String(data,"UTF-8").trim();
			
			int age =raf.readInt();
			builder.append("<tr>");
			builder.append("<td>"+username+"</td>");
			builder.append("<td>"+password+"</td>");
			builder.append("<td>"+nickname+"</td>");
			builder.append("<td>"+age+"</td>");
			builder.append("<td><a href='toEditUser?username="+username+"'>修改</a></td>");
			builder.append("</tr>");
			}
			
			
			builder.append("</table>");
			builder.append("</center>");
			builder.append("</body>");
			builder.append("</html>");
			byte[] data =builder.toString().getBytes("UTF-8");
			
			response.putHeader("Content-Type", "text/html");
			response.putHeader("Content-Length", data.length+"");
			response.setData(data);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}