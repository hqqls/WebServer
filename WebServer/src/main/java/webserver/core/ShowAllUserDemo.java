package webserver.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * ��ʾuser.txt�ļ��е������û���Ϣ
 * 
 * 
 * 
 */
public class ShowAllUserDemo {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("user.dat","r");
		//��ȡ�û���
		for(int i =0;i<raf.length()/100;i++){
			byte[] data = new byte[32];
			raf.read(data);
			String username = new String(data,"utf-8").trim();
			//	System.out.println(username);
			data = new byte[32];
			raf.read(data);
			//��ȡ����
			String password = new String(data,"utf-8").trim();
			//System.out.println(password );
			data = new byte[32];
			raf.read(data);
			//��ȡ�ǳ�
			String nickname = new String(data,"utf-8").trim();
			//��ȡ����
			int age = raf.readInt();
		
			System.out.println("user:\t"+username+"password:\t"+password+"nickname:\t"+nickname+"age��\t"+age);
		}
		raf.close();
	}

}
