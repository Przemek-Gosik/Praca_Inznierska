package com.example.brainutrain.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Slf4j
@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmailWithCode(String code,String emailAddress,String userName){
        String message = "<body style='font-family:sans-serif;font-size:12px;'>"
                +"<h3>Czesc "+userName+",</h3<br>"
                +"<p>Witamy w serwisie brainUtrain! Aby moc kontynuowac korzystanie "
                +"ze strony potwierdz swoj adres email, wpisujac podany nizej kod</p>"
                +"<p>Twoj kod weryfikacyjny:</p>"
                +"<h1 align='center'>"+code+"</h1></body>";
        String subject = "Email confirmation code";
        sendMessage(emailAddress,subject,message);
    }


    public void sendMessage(String to,String subject,String text){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    false,"utf-8");
            mimeMessage.setContent(text,"text/html");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException ex){
            log.warn(ex.getMessage());
        }
    }

}
