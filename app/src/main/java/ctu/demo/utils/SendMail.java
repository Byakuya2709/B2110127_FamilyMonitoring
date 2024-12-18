package ctu.demo.utils;

import android.util.Log;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {

    public static void sendEmail(String to, String subject, String messageText) {
        // Cấu hình SMTP cho Gmail
        String host = "smtp.gmail.com";
        String from = "minhkhanh10127@gmail.com";  // Địa chỉ email
        String password = "ovih mewy azmy fgog"; // Mật khẩu ứng dụng

        // Cấu hình thuộc tính SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // Cổng TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Tạo phiên làm việc SMTP
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Tạo email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(messageText);

            // Gửi email
            Transport.send(message);
            Log.d("JavaMail", "Email sent successfully.");
        } catch (MessagingException e) {
            Log.e("JavaMail", "Error: " + e.getMessage());
        }
    }

}
