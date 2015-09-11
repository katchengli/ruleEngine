package ruleEngine.services;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import quickforms.dao.Logger;

public class NotificationService {
	
	/**
	 * Sends a simple text email through Gmail. Requires a Gmail based address as the senderEmail.
	 * @param subject
	 * @param message
	 * @param recipientEmail
	 * @param senderEmail Must be a Gmail based address.
	 * @param appName
	 * @return a <code>boolean</code> value indicating whether the email was successfully sent.
	 */
	public boolean sendEmailFromGmail(String subject, String message, String recipientEmail, String senderEmail, 
			String appName, Authenticator authenticator){
		boolean success = true;
		Email email = new SimpleEmail();
		
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthentication("autoReplyPregApp@gmail.com", "autoReplyPregApp1");
		//email.setAuthenticator(authenticator);
		email.setSSLOnConnect(true);
		email.setSubject(subject);
		email.setSSLOnConnect(true);
		email.setStartTLSEnabled(true);
		email.setDebug(true);
		
		try {
/*			MimeMessage msg = new MimeMessage(email.getMailSession());
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
			Transport transport = email.getMailSession().getTransport("smtps");
			transport.connect("smtp.gmail.com", 465, "autoreplypregapp@gmail.com", "autoReplyPregApp1");
			Logger.log("NotificationService", "after connect");
			transport.send(msg, msg.getAllRecipients());*/
			
			email.getMailSession().getProperties().put("mail.smtp.auth", true);
			email.getMailSession().getProperties().put("mail.smtp.starttls.enable", true);
			email.setFrom(senderEmail);
			email.setMsg(message);
			email.addTo(recipientEmail);
			email.send();
		} catch (EmailException e) {
			Logger.log(appName, e);
			success = false;
		} /*catch (NoSuchProviderException e) {
			Logger.log(appName, e);
			success = false;
		} catch (MessagingException e) {
			Logger.log(appName, e);
			success = false;
		}*/
		
		return success;
	}
}
