package com.bridgelabz.user.sendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Ashwini Patil
 *
 */
@Service
public class SendMail {

	@Autowired
	private  JavaMailSender javaMailSender;

	/**
	 * send email while user login 
	 * @param to
	 * @param subject
	 * @param msg
	 */
	public void sendMail(String to, String subject, String msg)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);

		try
		{
			javaMailSender.send(message);
			System.out.println("Mail Send Scussfully!!!");

		}catch (Exception e) {

			System.out.println("Mail not send");
			e.printStackTrace();
		}
	}
}


