package org.example;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

public class SendEmail {
    public static void run() throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("config.properties"));

        String host = props.getProperty("SMTP_SERVER");
        String port = props.getProperty("SMTP_PORT");
        String from = props.getProperty("EMAIL_SENDER");
        String password = props.getProperty("EMAIL_PASSWORD");
        String[] toList = props.getProperty("EMAIL_RECIPIENTS").split(",");

        Properties sessionProps = new Properties();
        sessionProps.put("mail.smtp.auth", "true");
        sessionProps.put("mail.smtp.starttls.enable", "true");
        sessionProps.put("mail.smtp.host", host);
        sessionProps.put("mail.smtp.port", port);

        Session session = Session.getInstance(sessionProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] addresses = Arrays.stream(toList).map(String::trim).map(t -> {
            try {
                return new InternetAddress(t);
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }).toArray(InternetAddress[]::new);
        msg.setRecipients(Message.RecipientType.TO, addresses);
        msg.setSubject("ETL Output ZIP");

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText("Please find attached the ETL ZIP.");

        MimeBodyPart filePart = new MimeBodyPart();
        filePart.attachFile("output.zip");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(filePart);
        msg.setContent(multipart);

        Transport.send(msg);
        System.out.println("✅ Email sent successfully.");
    }
}
