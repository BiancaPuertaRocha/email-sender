package com.mail.email.services;

import com.mail.email.enums.EmailStatus;
import com.mail.email.models.Email;
import com.mail.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public Email sendEmail(Email email){
        email.setEmailSendDate(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            emailSender.send(message);
            email.setEmailStatus(EmailStatus.SENT);
        }catch (MailException e){
            email.setEmailStatus(EmailStatus.ERROR);
        }finally {
            return emailRepository.save(email);
        }
    }
}
