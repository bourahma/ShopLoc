package com.mimka.shoplocbe.emailing;

import com.mimka.shoplocbe.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl (JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void sendWelcomeEmail(User user) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        String content = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>New Trainer Registration Confirmation</title>
                </head>
                <body style="font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4;">
                                
                    <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
                        <h2 style="color: #333;">Welcome to Pokémon Trainer Club!</h2>
                        <p style="color: #555; font-size: 16px; line-height: 1.6;">Dear [[trainerName]],</p>
                        <p style="color: #555; font-size: 16px; line-height: 1.6;">Thank you for registering with Pokémon Trainer Club. You are now part of an exciting community of trainers!</p>
                        <p style="color: #555; font-size: 16px; line-height: 1.6;">Your Pokémon journey is about to begin. Get ready to catch 'em all and become a Pokémon Master!</p>
                        <p style="color: #555; font-size: 16px; line-height: 1.6;">If you have any questions or need assistance, feel free to contact our support team.</p>
                        <p style="color: #555; font-size: 16px; line-height: 1.6;">Best regards,<br> Pokémon Trainer Club Team</p>
                    </div>
                                
                </body>
                </html>            
                """;

        content = content.replace("[[trainerName]]", user.getFirstname());

        helper.setText(content, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Confirmation d'inscription");
        helper.setFrom(new InternetAddress("test@gmail.com"));

        javaMailSender.send(message);

    }

}
