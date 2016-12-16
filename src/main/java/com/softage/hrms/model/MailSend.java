package com.softage.hrms.model;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailSend {

private MailSender mailsender;
	
	public void setMailSender(MailSender mailsender)
	{
		this.mailsender=mailsender;
	}
	public void sendMail(String from,String to,String subject,String msg)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailsender.send(message);
		
	}
}
