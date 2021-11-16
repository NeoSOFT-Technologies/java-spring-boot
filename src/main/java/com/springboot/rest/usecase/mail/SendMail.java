package com.springboot.rest.usecase.mail;

import com.springboot.rest.domain.port.api.MailServicePort;
import com.springboot.rest.infrastructure.entity.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SendMail {

    private final MailServicePort mailServicePort;

    public SendMail(MailServicePort mailServicePort) {
        this.mailServicePort = mailServicePort;
    }

    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
    	mailServicePort.sendEmail(to, subject, content, isMultipart, isHtml);
    }
    
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
    	mailServicePort.sendEmailFromTemplate(user, templateName, titleKey);
    }

    public void sendActivationEmail(User user) {
    	mailServicePort.sendActivationEmail(user);
    }
    
    public void sendCreationEmail(User user) {
    	mailServicePort.sendCreationEmail(user);
    }
    
    public void sendPasswordResetMail(User user) {
    	mailServicePort.sendPasswordResetMail(user);
    }
    
}
