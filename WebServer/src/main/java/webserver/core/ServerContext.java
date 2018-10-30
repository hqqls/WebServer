package webserver.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务端环境--相关配置信息
 * @author Administrator
 *
 */
public class ServerContext {
	private static  Map<String,String> SERVLET_MAPPING = new HashMap<String,String>();

	static{
		
		initServletMapping();
	}
	/**
	 * 初始化Servlet映射
	 */
	private static void initServletMapping(){
		/*
		 * 加载conf、servlet。xml
		 * 将每个<servlet>标签中的属性url的值zu
		 */
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/servlet.xml"));
			Element root = doc.getRootElement();
			System.out.println("获得root目录"+root+"的root开始");
			List<Element> list = root.elements("servlet");
		//	SERVLET_MAPPING.put("/myweb/reg","com.tedu.webserver.servlet.RegServlet");
		//	SERVLET_MAPPING.put("/myweb/login","com.tedu.webserver.servlet.LoginServlet");
			for(Element mime : list){
				String key = mime.attribute("url").getValue();
				//String key =mine.attributeValue（”url“）；
				String value = mime.attribute("className").getValue();
				SERVLET_MAPPING .put(key, value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
//		try {
//			SAXReader reader = new SAXReader();
//			Document doc = reader.read(new File("conf/web.xml"));
//			Element root = doc.getRootElement();
//			List<Element> list = root.elements("mime-mapping");
//			for(Element mime : list){
////				Element keyEle = mime.element("extension");
////				String key = keyEle.getText();
//				
//				String key = mime.elementText("extension");
//				String value = mime.elementText("mime-type");
//				MIME_TYPE_MAPPING.put(key, value);
//			}
	}
	/**
	 * 根据请求获取对应的Servlet名字
	 * @param url
	 * @return
	 */
	public static String getServletName(String url){
		return SERVLET_MAPPING.get(url);
	}
}
