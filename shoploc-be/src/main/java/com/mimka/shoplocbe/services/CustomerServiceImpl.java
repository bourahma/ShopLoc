package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.repositories.CustomerRepository;
import com.mimka.shoplocbe.repositories.RoleRepository;
import com.mimka.shoplocbe.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    private final CustomerRepository customerRepository;

    //private final PasswordEncoder passwordEncoder;

    private final DtoUtil dtoUtil;

    private final RoleRepository roleRepository;

    private final TokenRepository tokenRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,DtoUtil dtoUtil, RoleRepository roleRepository, TokenRepository tokenRepository) {
        this.customerRepository = customerRepository;
        //this.passwordEncoder = passwordEncoder;
        this.dtoUtil = dtoUtil;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        Customer customer = this.customerRepository.findByUsername(username);
        if (customer == null)  throw new BadCredentialsException("invalidEmailMessage");
        return customer;
    }

    private UserDetails getUserDetails(String username) {
        User user = this.customerRepository.findByUsername(username);
        if (user == null)  throw new BadCredentialsException ("invalidUsernameMessage");

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
        if (this.customerRepository.findByEmail(email) != null) {
            throw new RegistrationException("registrationEmailMessage");
        }
        if (this.customerRepository.findByUsername(password) != null) {
            throw new RegistrationException("registrationUsernameMessage");
        }
        return true;
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO, FidelityCard fidelityCard) throws RegistrationException {
        Customer customer = this.dtoUtil.toCustomer(customerDTO);
        // Check passwords are valid.
        /*if (this.dtoUtil.checkPasswords(customerDTO.getConfirmedPassword(), customerDTO.getPassword())) {
            // Encode password.
            String encodedPassword = passwordEncoder.encode(customerDTO.getPassword());
            customerDTO.setPassword(encodedPassword);
            customerDTO.setConfirmedPassword(encodedPassword);
        }*/

        if (this.emailAndUsernameUniquenessValid(customerDTO.getEmail(), customerDTO.getUsername())) {
            // Add user authorities.
            customer.setRole(this.roleRepository.findByRoleId(1L));
            // Disable the user until he confirms his registration.
            customer.setEnabled(false);
            //customer.setFidelityCard(fidelityCard);
            // User is saved.
            this.customerRepository.save(customer);
        }

        return customer;
    }

    public Customer enableCustomer (String uuid) {
        Token token = this.tokenRepository.findTokenByUuid(uuid);
        Customer customer = token.getCustomer();

        customer.setEnabled(true);
        this.customerRepository.save(customer);
        this.tokenRepository.delete(token);

        return customer;
    }


    /*public void sendCredentialsEmail (MerchantDTO merchantDTO) {
        try {
            this.mailService.sendCredentialToMerchant(merchantDTO);
        } catch (MessagingException e) {
            System.out.println("Sending email verification error : " + e.getMessage());
        }
    }*/
}
