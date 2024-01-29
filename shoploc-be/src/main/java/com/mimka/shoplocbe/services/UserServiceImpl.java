package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.*;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.repositories.RoleRepository;
import com.mimka.shoplocbe.repositories.TokenRepository;
import com.mimka.shoplocbe.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    
    private UserRepository userRepository;

    private final CommerceService commerceService;

    private RoleRepository roleRepository;

    private final DtoUtil dtoUtil;

    private final TokenRepository tokenRepository;

    private final MailServiceImpl mailService;

    private String invalidUsernameMessage = "Nom d\'utilisateur incorrect.";

    private String invalidEmailMessage = "Adresse e-mail incorrecte.";

    private String registrationEmailMessage = "L'adresse e-mail est déjà associée à un compte existant.";

    private String registrationUsernameMessage = "Le nom d'utilisateur est déjà utilisé.";

    @Autowired
    public UserServiceImpl (UserRepository userRepository, CommerceService commerceService, RoleRepository roleRepository, DtoUtil dtoUtil, TokenRepository tokenRepository, MailServiceImpl mailServiceImpl) {
        this.userRepository = userRepository;
        this.commerceService = commerceService;
        this.roleRepository = roleRepository;
        this.dtoUtil = dtoUtil;
        this.tokenRepository = tokenRepository;
        this.mailService = mailServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null)  throw new BadCredentialsException (invalidEmailMessage);
        return user;
    }

    public User user (String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null)  throw new BadCredentialsException (registrationUsernameMessage);
        return user;
    }

    private UserDetails getUserDetails(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null)  throw new BadCredentialsException (invalidUsernameMessage);

        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                authorities);
    }

    public boolean emailAndUsernameUniquenessValid (String email, String password) throws RegistrationException {
        if (this.userRepository.findByEmail(email) != null) {
            throw new RegistrationException(registrationEmailMessage);
        }
        if (this.userRepository.findByUsername(password) != null) {
            throw new RegistrationException(registrationUsernameMessage);
        }
        return true;
    }

    @Override
    public Administrator createAdministrator(AdministratorDTO administratorDTO) throws RegistrationException {
        Administrator administrator = this.dtoUtil.toAdministrator(administratorDTO);
        if (this.emailAndUsernameUniquenessValid(administratorDTO.getEmail(), administratorDTO.getUsername())) {
            // Add user authorities.
            administrator.setRole(this.roleRepository.findByRoleId(1L));
            // Disable the user until he confirms his registration.
            administrator.setEnabled(false);
            // User is saved.
            this.userRepository.save(administrator);
        }

        this.sendVerificationEmail(administrator);
        return administrator;
    }

    @Override
    public Merchant createMerchant(MerchantDTO merchantDTO) throws RegistrationException {
        Merchant merchant = this.dtoUtil.toMerchant(merchantDTO);
        if (this.emailAndUsernameUniquenessValid(merchantDTO.getEmail(), merchantDTO.getUsername())) {
            // Add user authorities.
            merchant.setRole(this.roleRepository.findByRoleId(1L));
            // Disable the user until he confirms his registration.
            merchant.setEnabled(true);
            // Subscription date.
            merchant.setSubscriptionDate(LocalDate.now());
            merchant.setCommerce(this.commerceService.getCommerce(merchantDTO.getCommerceId()));
            // User is saved.
            this.userRepository.save(merchant);
        }
        return merchant;
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) throws RegistrationException {
        Customer customer = this.dtoUtil.toCustomer(customerDTO);
        if (this.emailAndUsernameUniquenessValid(customerDTO.getEmail(), customerDTO.getUsername())) {
            // Add user authorities.
            customer.setRole(this.roleRepository.findByRoleId(1L));
            // Disable the user until he confirms his registration.
            customer.setEnabled(false);
            // User is saved.
            this.userRepository.save(customer);
        }
        this.sendVerificationEmail(customer);
        return customer;
    }

    public void enableUser (String uuid) {
        Token token = this.tokenRepository.findTokenByUuid(uuid);
        if (token != null) {
            User user = token.getUser();
            user.setEnabled(true);
            this.userRepository.save(user);
            this.tokenRepository.delete(token);
            try {
                this.mailService.sendWelcomeEmail(user);
            } catch (MessagingException e) {
                System.out.println("Sending welcome e-mail : " + e.getMessage());
            }
        }
    }

    public void sendVerificationEmail (User user) {
        String uuid = UUID.randomUUID().toString();

        Token token = new Token();

        token.setUuid(uuid);
        token.setUser(user);

        this.tokenRepository.save(token);
        try {
            this.mailService.sendAccountValidationEmail(user, uuid);
        } catch (MessagingException e) {
            System.out.println("Sending email verification error : " + e.getMessage());
        }
    }

    public void sendCredentialsEmail (MerchantDTO merchantDTO) {
        try {
            this.mailService.sendCredentialToMerchant(merchantDTO);
        } catch (MessagingException e) {
            System.out.println("Sending email verification error : " + e.getMessage());
        }
    }
}