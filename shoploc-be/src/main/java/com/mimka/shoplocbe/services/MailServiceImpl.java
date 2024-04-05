package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MailServiceImpl {
    private final JavaMailSender javaMailSender;

    @Value("${be.url}")
    private String beUrl;

    @Value("${spring.mail.username}")
    private String emailFrom;
    @Autowired
    public MailServiceImpl (JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void triggerWelcomeEmail(User user) throws MessagingException {
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
                    <h2 style="color: #333;">Bienvenue chez ShopLoc !</h2>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Cher(ère) [[firstname]],</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Votre inscription sur ShopLoc a été confirmée avec succès !</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Nous sommes ravis de vous compter parmi notre communauté. Vous pouvez dès maintenant profiter pleinement de votre expérience d'achat sur ShopLoc.</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Pour toute question ou besoin d'assistance, n'hésitez pas à contacter notre service client.</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Merci de choisir ShopLoc pour vos achats en ligne.</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Cordialement,</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">L'équipe ShopLoc</p>
                </div>
                                
                </body>
                </html>            
                """;

        content = content.replace("[[firstname]]", user.getFirstname());

        helper.setText(content, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Confirmation d'inscription");
        helper.setFrom(new InternetAddress(emailFrom));

        javaMailSender.send(message);

    }
    @Async
    public void triggerEmailVerification(Customer user, String uuid) throws MessagingException {
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
                    <h2 style="color: #333;">Bienvenue chez ShopLoc !</h2>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Cher(ère) [[firstname]],</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Merci de vous être inscrit(e) sur ShopLoc. Veuillez confirmer votre inscription en cliquant sur le lien ci-dessous :</p>
                    <a href="[[link]]" style="background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-size: 18px;">Confirmer mon inscription</a>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Si vous avez des questions, n'hésitez pas à nous contacter.</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Cordialement,</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">L'équipe ShopLoc</p>
                </div>
                                
                </body>
                </html>            
                """;

        content = content.replace("[[firstname]]", user.getFirstname());
        content = content.replace("[[link]]", beUrl + "/authentication/customer/register/" + uuid);

        helper.setText(content, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Veuillez confirmer votre inscription");
        helper.setFrom(new InternetAddress(emailFrom));

        javaMailSender.send(message);
    }

    @Async
    public void triggerCredentialsEmail (MerchantDTO merchantDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        String content = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Confirmation d'inscription chez ShopLoc</title>
                </head>
                <body style="font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4;">
                               \s
                <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
                    <h2 style="color: #333;">Bienvenue chez ShopLoc !</h2>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Cher(e) commerçant(e),</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Votre inscription sur ShopLoc a été confirmée avec succès !</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Vous pouvez maintenant vous connecter à votre espace commerçant en utilisant les informations suivantes :</p>
                    <ul>
                        <li><strong>Identifiant :</strong> [[username]]</li>
                        <li><strong>Mot de passe :</strong> [[password]]</li>
                    </ul>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Nous vous encourageons à vous connecter dès maintenant pour accéder à votre espace commerçant et commencer à gérer votre boutique.</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Pour toute question ou besoin d'assistance, n'hésitez pas à contacter notre service client.</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Merci de choisir ShopLoc pour vos ventes en ligne.</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">Cordialement,</p>
                    <p style="color: #555; font-size: 16px; line-height: 1.6;">L'équipe ShopLoc</p>
                </div>
                               \s
                </body>
                </html>
                         
                """;

        content = content.replace("[[firstname]]", merchantDTO.getFirstname());
        content = content.replace("[[username]]", merchantDTO.getUsername());
        content = content.replace("[[password]]", merchantDTO.getPassword());

        helper.setText(content, true);
        helper.setTo(merchantDTO.getEmail());
        helper.setSubject("Céation de votre compte commerçant");
        helper.setFrom(new InternetAddress(emailFrom));

        javaMailSender.send(message);
    }

    @Async
    public void triggerPromotionToCustomers(Promotion promotion, Commerce commerce, List<Customer> customers) throws MessagingException {
        for (Customer customer : customers) {
            sendPromotionEmail(customer, commerce, promotion);
        }
    }

    private void sendPromotionEmail(Customer customer, Commerce commerce, Promotion promotion) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Découvrez notre nouvelle promotion exclusive chez " + commerce.getCommerceName() + " !";
        String content = buildPromotionContent(customer, commerce, promotion);

        helper.setText(content, true);
        helper.setTo(customer.getEmail());
        helper.setSubject(subject);
        helper.setFrom(new InternetAddress(emailFrom));

        javaMailSender.send(message);
    }

    private String buildPromotionContent(Customer customer, Commerce commerce, Promotion promotion) {
        String greeting = String.format("Cher(ère) %s,", customer.getFirstname());
        String introCommerce = String.format("Voici une offre spéciale de %s, ouvert de %s à %s.",
                commerce.getCommerceName(),
                commerce.getOpeningHour(),
                commerce.getClosingHour());
        String detail;

        if ("discount".equalsIgnoreCase(promotion.getPromotionType())) {
            detail = String.format("Profitez d'une réduction de %d%% sur les produits sélectionnés jusqu'au %s.",
                    promotion.getDiscountPercent(),
                    promotion.getEndDate().toString());
        } else {
            detail = String.format("Obtenez %d articles gratuits pour l'achat de %d articles jusqu'au %s.",
                    promotion.getOfferedItems(),
                    promotion.getRequiredItems(),
                    promotion.getEndDate().toString());
        }

        String signature = "Cordialement, <br> L'équipe de " + commerce.getCommerceName();
        String content = String.format("""
            <!DOCTYPE html>
            <html lang="fr">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Information sur la promotion</title>
            </head>
            <body>
                <p>%s</p>
                <p>%s</p>
                <p>%s</p>
                %s
         
            </body>
            </html>
            """, greeting, introCommerce, detail, signature);

        return content;
    }

    @Async
    public void triggerCommerceHoursChange(Commerce commerce, List<Customer> customers) throws MessagingException {
        for (Customer customer : customers) {
            sendHoursChangeEmail(customer, commerce);
        }
    }

    private void sendHoursChangeEmail(Customer customer, Commerce commerce) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Changement d'horaires chez " + commerce.getCommerceName();
        String content = buildHoursChangeContent(customer, commerce);

        helper.setText(content, true);
        helper.setTo(customer.getEmail());
        helper.setSubject(subject);
        helper.setFrom(new InternetAddress(emailFrom));

        javaMailSender.send(message);
    }

    private String buildHoursChangeContent(Customer customer, Commerce commerce) {
        String greeting = String.format("Cher(ère) %s,", customer.getFirstname());
        String introCommerce = String.format("Nous voulons vous informer des nouveaux horaires de %s.",
                commerce.getCommerceName());
        String detail = String.format("Nos nouvelles heures d'ouverture sont de %s à %s.",
                commerce.getOpeningHour(), commerce.getClosingHour());
        String signature = "Cordialement,\nL'équipe de " + commerce.getCommerceName();

        String content = String.format("""
            <!DOCTYPE html>
            <html lang="fr">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Changement d'horaires</title>
            </head>
            <body>
                <p>%s</p>
                <p>%s</p>
                <p>%s</p>
                <p>%s</p>
            </body>
            </html>
            """, greeting, introCommerce, detail, signature);

        return content;
    }

    @Async
    public void triggerMerchantsProductOutOfStock(Product product, List<Merchant> merchants) throws MessagingException {
        for (Merchant merchant : merchants) {
            sendProductOutOfStockEmail(merchant, product);
        }
    }

    private void sendProductOutOfStockEmail(Merchant merchant, Product product) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String subject = "Alerte de rupture de stock pour " + product.getProductName();
        String content = buildProductOutOfStockContent(merchant, product);

        helper.setText(content, true);
        helper.setTo(merchant.getEmail());
        helper.setSubject(subject);
        helper.setFrom(new InternetAddress(emailFrom));

        javaMailSender.send(message);
    }

    private String buildProductOutOfStockContent(Merchant merchant, Product product) {
        String greeting = String.format("Cher(ère) %s,", merchant.getFirstname());
        String intro = "Nous vous informons d'une rupture de stock pour un de vos produits.";
        String detail = String.format("Le produit \"%s\" est maintenant en rupture de stock. Veuillez reconstituer le stock pour continuer les ventes.",
                product.getProductName());
        String signature = "Cordialement,\nL'équipe de gestion de stock ShopLoc";

        String content = String.format("""
            <!DOCTYPE html>
            <html lang="fr">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Alerte de rupture de stock</title>
            </head>
            <body>
                <p>%s</p>
                <p>%s</p>
                <p>%s</p>
                <p>%s</p>
            </body>
            </html>
            """, greeting, intro, detail, signature);

        return content;
    }

}
