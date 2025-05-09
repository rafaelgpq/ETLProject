package org.example.mail;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

public class SendEmail {

    private static final String CONFIG_FILE = "config.properties";
    private static final String ATTACHMENT = "output.zip";
    private static final String SUBJECT = "ETL Output ZIP";
    private static final String BODY_TEXT = "Please find attached the ETL ZIP.";

    public static void run() throws Exception {
        Properties props = loadProperties(CONFIG_FILE);

        String host = props.getProperty("SMTP_SERVER");
        String port = props.getProperty("SMTP_PORT");
        String from = System.getenv("EMAIL_SENDER");
        String password = System.getenv("EMAIL_PASSWORD");
        String[] toList = props.getProperty("EMAIL_RECIPIENTS").split(",");

        Session session = createSession(host, port, from, password);
        Message msg = buildMessage(session, from, toList, SUBJECT, BODY_TEXT, new File(ATTACHMENT));

        Transport.send(msg);
        System.out.println("✅ Email sent successfully.");
    }

    public static Message buildMessage(Session session, String from, String[] toList,
                                       String subject, String body, File attachment) throws Exception {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, toInternetAddresses(toList));
        msg.setSubject(subject);

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body);

        MimeBodyPart filePart = new MimeBodyPart();
        filePart.attachFile(attachment);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(filePart);

        msg.setContent(multipart);
        return msg;
    }

    private static Properties loadProperties(String path) throws Exception {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
        }
        return props;
    }

    private static Session createSession(String host, String port, final String user, final String pass) {
        Properties sessionProps = new Properties();
        sessionProps.put("mail.smtp.auth", "true");
        sessionProps.put("mail.smtp.starttls.enable", "true");
        sessionProps.put("mail.smtp.host", host);
        sessionProps.put("mail.smtp.port", port);

        return Session.getInstance(sessionProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
    }

    private static InternetAddress[] toInternetAddresses(String[] emails) {
        return Arrays.stream(emails)
                .map(String::trim)
                .map(email -> {
                    try {
                        return new InternetAddress(email);
                    } catch (AddressException e) {
                        throw new RuntimeException("Invalid email address: " + email, e);
                    }
                })
                .toArray(InternetAddress[]::new);
    }
}
