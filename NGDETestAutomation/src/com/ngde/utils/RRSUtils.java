package com.ngde.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class RRSUtils {
	public static String convertXMLFileToString(String fileLocation,String fileName)throws FileNotFoundException,IOException, 
	SAXException, ParserConfigurationException, TransformerException, TransformerFactoryConfigurationError 
    { 
      
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
        InputStream inputStream = new FileInputStream(new File(fileLocation+fileName)); 
        Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream); 
        StringWriter stw = new StringWriter(); 
        Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
        serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
        return stw.toString(); 
      
    }
	
	@SuppressWarnings("resource")
	public static String convertXMLFileToStr(String fileLocation,String fileName) throws FileNotFoundException,
								IOException{
		
		String fileAsString = null;
		
		InputStream is;
		
			is = new FileInputStream(fileLocation+fileName);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line;
		
				line = buf.readLine();
			
			StringBuilder sb = new StringBuilder();
			        
			while(line != null){
			   sb.append(line).append("\n");
			   line = buf.readLine();
			}
			        
			 fileAsString = sb.toString();
						
		
		return fileAsString;
		
	}
	
	

}
