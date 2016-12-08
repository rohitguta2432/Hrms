package com.softage.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {
	
	@Autowired
	private MailSender mailSender;
	
	public void sendEmail(String toAddress, String fromAddress, String subject, String msgBody) {
		 
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromAddress);
		mailMessage.setTo(toAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(msgBody);
		mailSender.send(mailMessage);
	}

}
