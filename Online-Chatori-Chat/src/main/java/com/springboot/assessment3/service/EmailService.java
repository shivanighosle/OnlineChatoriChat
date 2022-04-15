package com.springboot.assessment3.service;

import java.util.Properties;


import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public boolean sendEmail(String subject, String message, String to) {
		
		boolean flag = false;
		
		String from = "aakashbhagore188@gmail.com";
		String password = "Aakash@123";
		String host = "smtp.gmail.com";
		
		
		//get the system information to properties addObject
		Properties properties = new Properties();
		System.out.println("PROPERTIES: "+ properties); 
		
		
		//if your are using http then you have to change your port number on 465 
		//but if you're using htts the you should use 587 port.
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	 
		//step 1 to get the session object....
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
			
		});
		
		//step2: compare the message[text,multi media]        
		try {
			Message m = new MimeMessage(session);
			//from email address 
			//mass.setFrom(new InternetAddress(fromEmail));
			m.setFrom(new InternetAddress(from)); 	
			//adding recienpient to message  
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			//adding subject to message
			m.setSubject(subject);
			//adding text to meassage 
			m.setText(message); 
			//sendEmail
			Transport.send(m);
			System.out.println("send message successfully.......... ");
			flag = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;	
	}
}
