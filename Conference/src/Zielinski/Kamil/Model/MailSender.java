package Zielinski.Kamil.Model;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender
{

	public void sentMail(String mail,String nameFile)
	{

		final String username = "testconferenceproject@gmail.com";
		final String password = "lolek123";

		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});

		try
		{

			MimeBodyPart messageBodyPart = new MimeBodyPart();

	        Multipart multipart = new MimeMultipart();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Conference"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
			DataSource source = new FileDataSource(nameFile);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName("Conference_Timetable");
	        multipart.addBodyPart(messageBodyPart);
			message.setSubject("Conference timetable");
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Hello!\nAutomatic message with Conference timetable file\nDo not reply\n\nHave a nice day");
			multipart.addBodyPart(textBodyPart);
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Done");

		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}
}