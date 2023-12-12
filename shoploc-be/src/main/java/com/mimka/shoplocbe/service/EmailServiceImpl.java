package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.HandleMailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RegistrationTokenService registrationTokenServiceImpl;

    @Value("${be.url}")
    private String beUrl;

    @Async
    protected void sendVerificationEmail(User user) throws MessagingException, HandleMailSendException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        String uuid = this.registrationTokenServiceImpl.setUserVerificationToke(user);
        String url = beUrl + "/authentication/verify/" + uuid;
        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "<style>body{font-family:'Arial',sans-serif;margin:20px;padding:20px;background-color:#f5f5f5}p{font-size:16px;line-height:1.5;color:#333;margin-bottom:15px}a{color:#007bff;text-decoration:none;font-weight:bold}a:hover{text-decoration:underline}.footer{margin-top:20px;font-size:14px;color:#777}</style>" +
                "</head>\n" +
                "<body>\n" +
                "    <p>Cher(e) [[lastname]],</p>\n" +
                "    \n" +
                "    <p>Nous sommes ravis que vous ayez rejoint notre communauté. Pour finaliser la création de votre compte, veuillez <a href=\"[[URL]]\" target=\"_self\">valider</a> votre inscription.</p>\n" +
                "    \n" +
                "    <p>Si vous n'arrivez pas à cliquer sur le lien, copiez et collez le lien suivant dans votre navigateur :</p>\n" +
                "    \n" +
                "    <p>[[URL]]</p>\n" +
                "    \n" +
                "    <p>Merci pour votre confiance</p>\n" +
                "    \n" +
                "    \n" +
                "    <p>A bientôt chez Shoploc !</p>\n" +
                "</body>\n" +
                "</html>\n";

        content = content.replace("[[URL]]", url);
        content = content.replace("[[lastname]]", user.getLastname());

        helper.setText(content, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Veuillez confirmer votre inscription");
        helper.setFrom("ShopLoc");

        // Send the email
        try {
            javaMailSender.send(message);
        } catch (MailSendException e) {
            System.out.println(e.getMessage());
            throw new HandleMailSendException("Invalid Address e-mail");
        }

    }

    @Async
    protected void resendVerificationEmail(User user) throws MessagingException, HandleMailSendException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        String uuid = this.registrationTokenServiceImpl.resetVerificationToken(UUID.randomUUID().toString(), user);
        String url = beUrl + "/registration/verify/" + uuid;
        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "    <p>Cher(e) [[lastname]],</p>\n" +
                "    \n" +
                "    <p>Nous sommes ravis que vous ayez rejoint notre communauté. Pour finaliser la création de votre compte, veuillez <a href=\"[[URL]]\" target=\"_self\">valider</a> votre inscription.</p>\n" +
                "    \n" +
                "    <p>Si vous n'arrivez pas à cliquer sur le lien, copiez et collez le lien suivant dans votre navigateur :</p>\n" +
                "    \n" +
                "    <p>[[URL]]</p>\n" +
                "    \n" +
                "    <p>Merci pour votre confiance</p>\n" +
                "    \n" +
                "    \n" +
                "    <p>A bientôt chez Shoploc !</p>\n" +
                "</body>\n" +
                "</html>\n";

        content = content.replace("[[URL]]", url);
        content = content.replace("[[lastname]]", user.getLastname());

        helper.setText(content, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Veuillez confirmer votre inscription");
        helper.setFrom("ShopLoc");

        // Send the email
        try {
            javaMailSender.send(message);
        } catch (MailSendException e) {
            throw new HandleMailSendException("Invalid Address e-mail");
        }

    }
}
