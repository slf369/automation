package com.demo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



/**
 *
 * 读取配置文件automation.properties
 */

public final class ProjectProperties {	
	
	public  static Properties prop = readProjectProperties();
	public static final String TRANSFER_PATH=prop.getProperty("TRANSFER_PATH");
	
	public static Properties readProjectProperties() {
		Properties ps = new Properties();		
		String fileName = System.getProperty("user.dir")+File.separator+ "config" +File.separator+"automation.properties";

		try {
			InputStream is = new BufferedInputStream(new FileInputStream(fileName));
			ps.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ps;
	}
}
