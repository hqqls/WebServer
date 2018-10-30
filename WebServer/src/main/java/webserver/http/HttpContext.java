package webserver.http;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 协议中相关定义的内容
 * @author soft01
 *
 */
public class HttpContext {
	/*
	 *  状态代码与描述的映射关系
	 */
	private static Map<Integer,String> STATUS_CODE_REASON_MAPPING = new HashMap<Integer,String>();
	
	/*
	 * 介质类型映射
	 * key:实体资源后缀名
	 * value:MIME对应值，格式如:text/html
	 */
	private static Map<String,String> MIME_TYPE_MAPPING = new HashMap<String,String>();
	
	static{
		//初始化状态代码与描述的映射关系
		initStatuCodeReasonMapping();
		//初始化介质类型映射
		initMimeTypeMapping();
	}
	/**
	 * 初始化介质类型映射
	 */
	private static void initMimeTypeMapping(){
//		MIME_TYPE_MAPPING.put("html", "text/html");
//		MIME_TYPE_MAPPING.put("css", "text/css");
//		MIME_TYPE_MAPPING.put("js", "application/javascript");
//		MIME_TYPE_MAPPING.put("png", "image/png");
//		MIME_TYPE_MAPPING.put("jpg", "image/jpeg");
//		MIME_TYPE_MAPPING.put("gif", "image/gif");
		/*
		 * 1.读取conf/web.xml文件
		 * 2:获取根元素下所有名为<mime-mapping>的子元素
		 * 3:将每个<mime-mapping>元素下的子元素:
		 *   <extension>中间的文本作为key
		 *   <mime-type>中间的文本作为value
		 *   存入到MIME_TYPE_MAPPING,完成初始化操作
		 */
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/web.xml"));
			Element root = doc.getRootElement();
			List<Element> list = root.elements("mime-mapping");
			for(Element mime : list){
//				Element keyEle = mime.element("extension");
//				String key = keyEle.getText();
				
				String key = mime.elementText("extension");
				String value = mime.elementText("mime-type");
				MIME_TYPE_MAPPING.put(key, value);
			}
			//System.out.println("解析完毕");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * 初始化状态代码与类型的映射
	 */
	private static void initStatuCodeReasonMapping(){
		STATUS_CODE_REASON_MAPPING.put(200,"OK");
		STATUS_CODE_REASON_MAPPING.put(201,"Created");
		STATUS_CODE_REASON_MAPPING.put(202,"Accepted");
		STATUS_CODE_REASON_MAPPING.put(204,"No Content");
		STATUS_CODE_REASON_MAPPING.put(301,"Moved Permanently");
		STATUS_CODE_REASON_MAPPING.put(302,"Moved Temporarily");
		STATUS_CODE_REASON_MAPPING.put(304,"Not Modified");
		STATUS_CODE_REASON_MAPPING.put(400,"Bad Request");
		STATUS_CODE_REASON_MAPPING.put(401,"Unauthorized");
		STATUS_CODE_REASON_MAPPING.put(403,"Forbidden");
		STATUS_CODE_REASON_MAPPING.put(404,"Not Found");
		STATUS_CODE_REASON_MAPPING.put(500,"Internal Server Error");
		STATUS_CODE_REASON_MAPPING.put(501,"Not Implemented");
		STATUS_CODE_REASON_MAPPING.put(502,"Bad Gateway");
		STATUS_CODE_REASON_MAPPING.put(503,"Service Unavailable");
		
	}
	
	/**
	 * 根据给定的状态代码返回对应的状态描述
	 * @param statusCode
	 * @return
	 */
	public static String getStatusReason(int statusCode){
		return STATUS_CODE_REASON_MAPPING.get(statusCode);
	}
	
	/**
	 * 根据实体资源后缀名获取对应的介质类型定义
	 * @param ext
	 * @return
	 */
	public static String getMimeType(String ext){
		return MIME_TYPE_MAPPING.get(ext);
	}
	public static void main(String[] args){
		String str = getStatusReason(503);
		System.out.println(str);
		
		str = getMimeType("js");
		System.out.println(str);
	}
}
