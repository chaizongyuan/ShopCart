package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtils {
	/**
	 * jdbc��װ��
	 */
	static Properties p=new Properties();
	static{
		
		InputStream is=DBUtils.class.getResourceAsStream("/jdbc.properties");
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ����
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		String url=p.getProperty("url");
		String driverClass=p.getProperty("driverClass");
		String uname=p.getProperty("username");
		String password=p.getProperty("password");
		Class.forName(driverClass);
		//��¼�ɹ�
		Connection conn=DriverManager.getConnection(url, uname, password);
		return conn;
	}

}
