package webserver.servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.RandomAccessFile;

/**
 * 生成指定用户信息页面
 * @author Administrator
 *
 */
public class ToEditUserServlet extends HttpServlet{

	public void service(HttpRequest request, HttpResponse response) {
		//获取要修改用户的名字
		String username =request.getParameter("username");
		try {
			RandomAccessFile raf=new RandomAccessFile("user.dat","r");
			for(int i= 0;i<raf.length()/100;i++){
				raf.seek(i*100);
				byte[] data =new byte[32];
				raf.read(data);
				String name = new String(data,"utf-8").trim();
				if(name.equals(username)){
					raf.read(data);
					String password = new String(data,"utf-8").trim();
					raf.read(data);
					String nickname = new String(data,"utf-8").trim();
					int age  =raf.readInt();
					StringBuilder builder =new StringBuilder();
					builder.append("<html>");
					builder.append("<head>");
					builder.append("<title>用户列表</title>");
					builder.append("<meta charset='UTF-8'>");
					builder.append("</head>");
					builder.append("<body>");
					builder.append("<center>");
					builder.append("<h1>用户列表</h1>");
					builder.append("<form action='updateUser' method='post'>");
					builder.append("<table border ='1'>");
					builder.append("<tr>");
					builder.append("<td>用户名</td>");
					builder.append("<td >"+username+"<input type='hidden' name= 'username' value='"+username+"'>"+"</td>");
					builder.append("</tr>");
					builder.append("<tr>");
					builder.append("<td>密码</td>");
					builder.append("<td><input type='password' name= 'newpassword' value='"+password+"'></td>");
					builder.append("</tr>");
					builder.append("<tr>");
					builder.append("<td>昵称</td>");
					builder.append("<td><input type='text' name= 'newnickname' value='"+nickname+"'></td>");
					builder.append("</tr>");
					builder.append("<tr>");
					builder.append("<td>年龄</td>");
					builder.append("<td><input type='text' name= 'newage' value='"+age+"'></td>");
					builder.append("</tr>");
					builder.append("<tr>");
					builder.append("<td colspan=‘2' align='center'><input type= 'submit' value='修改'></td>");
					builder.append("</tr>");
					builder.append("</table>");
					builder.append("</form>");
					builder.append("</center>");
					builder.append("</body>");
					builder.append("</html>");
					
					byte[] arr =builder.toString().getBytes("UTF-8");
					
					response.putHeader("Content-Type", "text/html");
					response.putHeader("Content-Length", arr.length+"");
					response.setData(arr);
					System.out.println("lefhglekjrtgklj");
					break;
					
					}
				
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
