package com.softage.hrms.service.impl;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toAddress, String fromAddress, String subject, String msgBody) {
		String host="localhost";
		Properties properties=new Properties();
		properties.setProperty("mail.smtp.host", host);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromAddress);
		mailMessage.setTo(toAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(msgBody);
		mailSender.send(mailMessage);		
		
	}
	
}
