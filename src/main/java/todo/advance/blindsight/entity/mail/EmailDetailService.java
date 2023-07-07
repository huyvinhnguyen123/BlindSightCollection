package todo.advance.blindsight.entity.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailDetailService {
    // inject java mail sender
    private final JavaMailSender mailSender;

    // add google mail username
    @Value("${spring.mail.username}")
    private String sender;

    /**
     * * send verify mail when user create account
     * 
     * @param emailDetail
     */
    public void sendVerifyMail(EmailDetail emailDetail) {
        // Creating a mime message
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        // Try block to check for exception
        try{
            // Create mime message helper to send mail
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(emailDetail.getMsgBody(), true); // when set true this mean you can custom this text by html 
            mimeMessageHelper.setSubject(emailDetail.getSubject());
            mailSender.send(mimeMessage);
            log.info("Send mail SUCCESS!"); 
            
        }catch(MessagingException e){
            e.printStackTrace();
            log.error("Send mail FAIL!");
            log.trace("Make sure that your mail body is input and other mail property it's too!");  
        }
    }

    /**
     * * send reset email to user
     * 
     * @param emailDetail
     */
    public void sendResetMail(EmailDetail emailDetail) {
        // Creating a mime message
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        // Try block to check for exception
        try{
            // Create mime message helper to send mail
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(emailDetail.getMsgBody(), true); // when set true this mean you can custom this text by html 
            mimeMessageHelper.setSubject(emailDetail.getSubject());
            mailSender.send(mimeMessage);
            log.info("Send mail SUCCESS!"); 
            
        }catch(MessagingException e){
            e.printStackTrace();
            log.error("Send mail FAIL!");
            log.trace("Make sure that your mail body is input and other mail property it's too!");  
        }
    }
}
