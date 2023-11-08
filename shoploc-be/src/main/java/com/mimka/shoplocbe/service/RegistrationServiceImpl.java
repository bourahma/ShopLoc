package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.entity.RegistrationToken;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.repository.RegistrationTokenRepository;
import com.mimka.shoplocbe.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void register(UserDTO userDTO) throws EmailAlreadyUsedException, MessagingException {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        User user = this.userServiceImpl.createUser(userDTO);
        this.sendVerificationEmail(user);
    }

    @Async
    protected void sendVerificationEmail(User user) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        String uuid = this.setVerificationToken(user);
        String url = "http://localhost:8080/registration/verify/" + uuid;
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
        javaMailSender.send(message);
    }

    @Override
    @Transactional
    public boolean verify(String uuid) {
        RegistrationToken registrationToken = this.registrationTokenRepository.findByUuid(uuid);

        User user = registrationToken.getUser();
        if (user == null || user.getEnabled()) {
            return false;
        } else if (!user.getEnabled()) {
            this.registrationTokenRepository.deleteByUuid(uuid);
            user.setEnabled(true);
            this.userRepository.save(user);

            return true;
        }
        return false;
    }

    private String setVerificationToken (User user) {
        RegistrationToken registrationToken = new RegistrationToken();

        String uuid = UUID.randomUUID().toString();
        registrationToken.setUuid(uuid);
        registrationToken.setUser(user);

        this.registrationTokenRepository.save(registrationToken);

        return uuid;
    }
}
