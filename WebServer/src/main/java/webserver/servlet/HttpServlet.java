package webserver.servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.File;

/**
 * 所有Servlet的超类
 * @author soft01
 *
 */
public abstract class HttpServlet {
	public abstract void service(HttpRequest request,HttpResponse response);
	/**
	 * 跳转路径对应的资源
	 * @param path
	 */
	public void forward(String path,HttpRequest request,HttpResponse response){
		File file = new File("webapps"+path);
		response.setEntity(file);
	}
}
