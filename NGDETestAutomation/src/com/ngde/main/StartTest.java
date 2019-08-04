package com.ngde.main;
import java.io.File;
	
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import com.ngde.jms.Publisher;
import com.ngde.utils.LoadProperties;

public class StartTest {
	@SuppressWarnings("resource")
	public static void main(String[] args){
		
		Properties prop = new Properties();
		Publisher publisher = new Publisher();
		
		try {
			prop=LoadProperties.loadProperties();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to read properties file");
			System.exit(0);
			//return;
			//e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("IOException occurred");
			System.exit(0);
			//return;
			//e.printStackTrace();
		}
		
		
		int num=0;
		String fileName="";
	
		while (num==0){
			System.out.println("\nPlease give input 1/2/3\n1 Test one XML file by its name\n"
					+ "2 Test all XML files\n3 Exit program");
			try{
			num=new Scanner(System.in).nextInt();
			}
			catch(InputMismatchException ime ){
				num=4;
			}
			if(num==3){
				//return;
				System.exit(0);
			}
			else if(num==2){
				System.out.println("Are you sure you want to publish all files to the topic: Y/N");
				String flag=new Scanner(System.in).next();
				
				if(flag.equalsIgnoreCase("Y")){
				File folder = new File(prop.getProperty("xmlFileLocation"));			
				File[] listOfFiles = folder.listFiles();
								
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						fileName= listOfFiles[i].getName();
						publisher.publishXml(fileName); 
						}
					}
				
				}
				else if(flag.equalsIgnoreCase("N")){
					System.out.println("No files published");
				}
				else{
					System.out.println("Invalid input");
				}
				
				num=0;
			}
			else if(num==1){
				System.out.println("Enter"
						+ " file name");
				
				fileName=new Scanner(System.in).next();
				
				publisher.publishXml(fileName);;
				
				num=0;
			}
			
			else{
				System.out.println("Invalid input");
				num=0;
			}			
			
		}

	}

}
