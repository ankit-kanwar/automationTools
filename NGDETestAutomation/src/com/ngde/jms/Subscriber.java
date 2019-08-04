package com.ngde.jms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.ngde.utils.LoadProperties;

public class Subscriber {

	public static void main(String[] args) {

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

			MessageConsumer consumer =session.createConsumer(topic);


			MessageListener listner = new MessageListener() {
				public void onMessage(Message message) {
					try {
						if (message instanceof TextMessage) {
							TextMessage textMessage = (TextMessage) message;
							System.out.println("Received message"
									+ textMessage.getText() + "'");
						}
					} catch (JMSException e) {
						System.out.println("Caught:" + e);
						e.printStackTrace();
					}
				}
			};

			consumer.setMessageListener(listner);


			System.out.println("Press enter to close connection");
			System.in.read();



			//session.close();
			connection.close();


		} catch (JMSException e) {
			System.out.println("JMS Exception occurred ");
			//e.printStackTrace();

		}

		catch (FileNotFoundException e) {
			System.out.println("Unable to read propeties file");
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

