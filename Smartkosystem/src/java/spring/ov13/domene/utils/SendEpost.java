package spring.ov13.domene.utils;

import java.io.File;
import java.util.Properties;
import javax.mail.MessagingException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMailMessage;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;

public class SendEpost {

    public void sendEpost(String epost, String melding) {
        SimpleMailMessage meld = new SimpleMailMessage();
        meld.setText(melding);
        meld.setTo(epost);
        meld.setSubject("Glemt passord");
        JavaMailSenderImpl sender = new JavaMailSenderImpl() {
        };
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername("sksmailsender@gmail.com");
        sender.setPassword("sksmailsend");
        sender.setProtocol("smtp");
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.starttls.enable", "true");
        mailProperties.setProperty("mail.smtp.quitwait", "false");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailProperties.setProperty("mail.debug", "true");
        sender.setJavaMailProperties(mailProperties);

        sender.send(meld);

        System.out.println("Sender epost til: " + epost + ", melding: " + melding);

    }

    public void sendEpost(String epost, String tittel, String melding) {
        SimpleMailMessage meld = new SimpleMailMessage();
        meld.setText(melding);
        meld.setTo(epost);
        meld.setSubject(tittel);
        JavaMailSenderImpl sender = new JavaMailSenderImpl() {
        };
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername("sksmailsender@gmail.com");
        sender.setPassword("sksmailsend");
        sender.setProtocol("smtp");
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.starttls.enable", "true");
        mailProperties.setProperty("mail.smtp.quitwait", "false");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailProperties.setProperty("mail.debug", "true");
        sender.setJavaMailProperties(mailProperties);

        sender.send(meld);

        System.out.println("Sender epost til: " + epost + ", melding: " + melding);

    }

    public void sendEpost(String epost, String tittel, String melding, File f) {
        SimpleMailMessage meld = new SimpleMailMessage();
        meld.setText(melding);
        meld.setTo(epost);
        meld.setSubject(tittel);
        JavaMailSenderImpl sender = new JavaMailSenderImpl() {
        };
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername("sksmailsender@gmail.com");
        sender.setPassword("sksmailsend");
        sender.setProtocol("smtp");
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.starttls.enable", "true");
        mailProperties.setProperty("mail.smtp.quitwait", "false");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailProperties.setProperty("mail.debug", "true");
        sender.setJavaMailProperties(mailProperties);
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("sksmailsender@gmail.com");
            helper.setTo(epost);
            helper.setSubject(tittel);
            helper.setText(melding);
            FileSystemResource file = new FileSystemResource(f);
            helper.addAttachment(file.getFilename(), file);
        } catch(MessagingException e){
            System.out.println(e);
        }
        sender.send(message);

        System.out.println("Sender epost til: " + epost + ", melding: " + melding);

    }

    public static void main(String[] args) {
        SendEpost se = new SendEpost();
        se.sendEpost("sksmailsender@gmail.com", "http://google.com");

    }

}
