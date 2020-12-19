package com.internetapplications.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${spring.mail.properties.emailName}")
    private String senderName;

    private static final Logger logger = Logger.getLogger(MailService.class.getName());

    /**
     * Example of sending email
     *
     * @Autowire this service -> mailService
     *
     * TextEmail email = new TextEmail(Subject, Body);
     * email.addAttachment(file)
     * mailService.sendEmail(new EmailRecipient("x@gmail.com", "X X"), email);
     *
     */

    public void sendEmail(EmailRecipient recipient, TextEmail email) {
        this.sendEmail(Arrays.asList(recipient), null, email.getSubject(), email.getText(), false, email.getAttachments());
    }

    public void sendEmail(EmailRecipient recipient, List<EmailRecipient> cc, TextEmail email) {
        this.sendEmail(Arrays.asList(recipient), cc, email.getSubject(), email.getText(), false, email.getAttachments());
    }

    public void sendEmail(List<EmailRecipient> recipients, TextEmail email) {
        this.sendEmail(recipients, null, email.getSubject(), email.getText(), false, email.getAttachments());
    }

    public void sendEmail(List<EmailRecipient> recipients, List<EmailRecipient> cc, TextEmail email) {
        this.sendEmail(recipients, cc, email.getSubject(), email.getText(), false, email.getAttachments());
    }

    public void sendEmail(List<EmailRecipient> emailRecipients, List<EmailRecipient> cc, String subject, String body, boolean html, List<File> attachments) {

        MimeMessagePreparator messagePreparator = (mimeMessage) -> {

            List<InternetAddress> addresses = emailRecipients == null ? new ArrayList() :
                    emailRecipients.stream().map(r -> createInternetAddress(r.getEmail(), r.getName()))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());


            List<InternetAddress> ccaddresses = cc == null ? new ArrayList() :
                    cc.stream().map(r -> createInternetAddress(r.getEmail(), r.getName()))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());


            if (!addresses.isEmpty()) {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, attachments.size() > 0);
                messageHelper.setFrom(new InternetAddress(this.senderEmail, this.senderName));
                messageHelper.setTo(addresses.toArray(new InternetAddress[0]));
                if (!ccaddresses.isEmpty()) {
                    messageHelper.setCc(ccaddresses.toArray(new InternetAddress[0]));
                }

                messageHelper.setSentDate(new Date());
                messageHelper.setSubject(subject);
                messageHelper.setText(body, html);
                mimeMessage.setHeader("XPriority", "1");
                Iterator iterator = attachments.iterator();
                while (iterator.hasNext()) {
                    File file = (File) iterator.next();
                    messageHelper.addAttachment(file.getName(), file);
                }
            }
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private InternetAddress createInternetAddress(String email, String name) {
        try {
            return new InternetAddress(email, name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
