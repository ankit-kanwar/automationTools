package com.ngde.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
	
	public static  Properties loadProperties() throws IOException,FileNotFoundException{
		
		Properties prop = new Properties();		
		InputStream input = null;		
		
		input = new FileInputStream("NgdeTestingConfig.properties");
		prop.load(input);
		
		return prop;
	}
	
	

}
