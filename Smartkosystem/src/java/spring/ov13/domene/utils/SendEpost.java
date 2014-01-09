package spring.ov13.domene.utils;

import java.util.Properties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


public class SendEpost {
    public void sendEpost(String epost, String melding){
        SimpleMailMessage meld = new SimpleMailMessage();
        meld.setText(melding);
        meld.setTo(epost);
        meld.setSubject("Glemt passord");
        JavaMailSenderImpl sender = new JavaMailSenderImpl() {} ;
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername("sksmailsender@gmail.com");
        sender.setPassword("sksmailsend");
        sender.setProtocol("smtp");
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.starttls.enable", "true");
        mailProperties.setProperty("mail.smtp.quitwait", "false");

        mailProperties.setProperty("mail.debug", "true");
        sender.setJavaMailProperties(mailProperties);
        
        
        sender.send(meld);
        
    }
    
    
    public static void main (String[] args){
        SendEpost se= new SendEpost();
        se.sendEpost("sksmailsender@gmail.com", "http://google.com");
    
    }
    
}
