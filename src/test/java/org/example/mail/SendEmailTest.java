package org.example.mail;

import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class SendEmailTest {

    private Properties dummyProps;
    private String[] recipients;
    private String sender;
    private File dummyAttachment;

    @BeforeEach
    void setup() throws Exception {
        // Create dummy zip file to attach
        dummyAttachment = new File("output.zip");
        try (OutputStream os = new FileOutputStream(dummyAttachment)) {
            os.write("dummy".getBytes());
        }

        dummyProps = new Properties();
        dummyProps.setProperty("SMTP_SERVER", "smtp.test.com");
        dummyProps.setProperty("SMTP_PORT", "587");
        sender = "test@sender.com";
        recipients = new String[]{"one@test.com", "two@test.com"};
    }

    @Test
    void shouldBuildMimeMessageWithCorrectHeadersAndAttachment() throws Exception {
        Session dummySession = Session.getDefaultInstance(new Properties());

        Message msg = SendEmail.buildMessage(
                dummySession,
                sender,
                recipients,
                "ETL Output ZIP",
                "Please find attached the ETL ZIP.",
                dummyAttachment
        );

        assertThat(msg.getSubject()).isEqualTo("ETL Output ZIP");
        assertThat(msg.getFrom()[0].toString()).isEqualTo(sender);
        assertThat(msg.getAllRecipients()).hasSize(2);
        assertThat(msg.getContent()).isInstanceOf(Multipart.class);
    }
}
