package spring.ov13.domene.utils;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


public class SendEpost {
    public void sendEpost(String epost, String melding){
        SimpleMailMessage meld = new SimpleMailMessage();
        meld.setText(melding);
        meld.setTo(epost);
        meld.setFrom("skssomething");
        MailSender sender = new JavaMailSenderImpl();
        sender.send(meld);
        
    }
    
}
