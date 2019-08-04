		package com.ngde.jms;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.ngde.utils.LoadProperties;
import com.ngde.utils.RRSUtils;

public class Publisher{

	public void publishXml(String xmlName){
		Properties prop = new Properties();		
		InputStream input = null;
		


		try {
			
			/*input = new FileInputStream("NgdeTestingConfig.properties");
			prop.load(input);*/
			prop = LoadProperties.loadProperties();
			
			ActiveMQConnectionFactory connectionFactory= new ActiveMQConnectionFactory(prop.getProperty("connectionFactory"));
			
			Connection connection = connectionFactory.createConnection();
			connection.start();

			Session session =connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination topic= session.createTopic(prop.getProperty("topicName"));

			MessageProducer producer =session.createProducer(topic);

			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			
			String xmlMessage=RRSUtils.convertXMLFileToStr(prop.getProperty("xmlFileLocation"),xmlName);

			//String strMessage="Premier JMS message with thread name:" + Thread.currentThread().getName();		
			//System.out.println("Sent message: "+textMessage.getText() +" Hash Code :"+ textMessage.hashCode() + " Thread:"+ Thread.currentThread().getName());


			TextMessage textMessage = session.createTextMessage(xmlMessage);
			producer.send(textMessage);
			System.out.println("Test case "+xmlName+" has been published successfully on the Topic.");

			session.close();
			connection.close();


		} catch (JMSException e) {
			System.out.println("JMS Exception occurred");
			//e.printStackTrace();

		}
		catch (FileNotFoundException e) {
			System.out.println(xmlName+" file is not available at the provided location");
			//e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("IO Exception occurred");
			//e.printStackTrace();
		}
		catch(Exception e){
            e.printStackTrace();
        }
				
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}


