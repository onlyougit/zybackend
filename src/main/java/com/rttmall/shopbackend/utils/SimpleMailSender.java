package com.rttmall.shopbackend.utils;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SimpleMailSender {

	static String EMAIL_ADDRESS = "sales@rttmall.com";
	// static String EMAIL_PASSWORD = "!!112252347";
	static String EMAIL_AUTHORIZATION_CODE = "XMgxkj2016";

	/**
	 * 发送激活邮件
	 * 
	 * @author Lixiaobao
	 *
	 * @param toAddress
	 * @param validateUrl
	 * @param requestContext
	 * @throws GeneralSecurityException
	 * @throws MessagingException
	 *
	 * @date 2017年2月20日下午3:57:44
	 */
	public static void sendActiveEmial(String toAddress, String validateUrl)
			throws GeneralSecurityException, MessagingException {
		// RequestContext requestContext = new RequestContext(request);
		String title = "欢迎注册RTT，请及时激活您的账号";
		// String user =
		// String title = props.getProperty("title") + "<br><br><br>";
		String cause = "您收到这封邮件，是因为您刚刚在RTT商城使用该邮箱账号注册，如果这不是您本人操作，请忽略本信息。<br><br><br>";
		String content = "如果是您本人的操作，请在24小时之内点击下面链接，来激活您的账号。<br><br><br>";
		String remind = "如果该链接不处于可点击网页链接状态，请复制它输入到浏览器的地址栏，并敲击回车。<br><br><br>";
		String end = "感谢您使用并注册RTT，希望您购物愉快。<br><br><br>";
		String url = new StringBuffer("<a href=\"").append(validateUrl).append("\" >").append(validateUrl)
				.append("</a><br><br><br>").toString();
		String email = new StringBuffer(cause).append(content).append(url).append(remind).append(end).toString();// 邮件
		sendEmail(title, email, toAddress, EMAIL_ADDRESS, EMAIL_AUTHORIZATION_CODE);
	}

	/**
	 * 发送验证码邮件
	 * 
	 * @author Lixiaobao
	 *
	 * @param title
	 * @param toAddress
	 * @param content
	 * @throws GeneralSecurityException
	 * @throws MessagingException
	 *
	 * @date 2017年2月20日下午3:57:28
	 */
	public static void sendCodeEmail(String title, String toAddress, String content)
			throws GeneralSecurityException, MessagingException {
		sendEmail(title, content, toAddress, EMAIL_ADDRESS, EMAIL_AUTHORIZATION_CODE);
	}

	static void sendEmail(String subject, String content, String toEmail, String formEmail, String password)
			throws GeneralSecurityException, MessagingException {
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.qq.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");
		// props.put("mail.smtp.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		// MailSSLSocketFactory sf = new MailSSLSocketFactory();
		// sf.setTrustAllHosts(true);
		// props.put("mail.smtp.ssl.enable", "true");
		// props.put("mail.smtp.ssl.socketFactory", sf);
		Session session = Session.getInstance(props);
		Message msg = new MimeMessage(session);
		try {
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(content, "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(formEmail));
			msg.setContent(mainPart); //
		} catch (Exception e) {
			e.printStackTrace();
		}
		Transport transport = session.getTransport(); //
		transport.connect("smtp.qq.com", 25, formEmail, password);
		transport.sendMessage(msg, new Address[] { new InternetAddress(toEmail) });
		transport.close();
	}

}
