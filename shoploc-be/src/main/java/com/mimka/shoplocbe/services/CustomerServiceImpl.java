package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.repositories.CustomerRepository;
import com.mimka.shoplocbe.repositories.FidelityCardRepository;
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

    private final FidelityCardRepository fidelityCardRepository;

    private final DtoUtil dtoUtil;

    private final RoleRepository roleRepository;

    private final TokenRepository tokenRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, FidelityCardRepository fidelityCardRepository, DtoUtil dtoUtil, RoleRepository roleRepository, TokenRepository tokenRepository) {
        this.customerRepository = customerRepository;
        this.fidelityCardRepository = fidelityCardRepository;
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
        if (this.emailAndUsernameUniquenessValid(customerDTO.getEmail(), customerDTO.getUsername())) {
            customer.setRole(this.roleRepository.findByRoleId(1L));
            customer.setEnabled(false);
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

    @Override
    public boolean orderSettled(double total) {
        return false;
    }

    @Override
    public boolean orderSettled(String customerUsername, double total, boolean usingPoints) {
        Customer customer = this.customerRepository.findByUsername(customerUsername);
        FidelityCard fidelityCard = customer.getFidelityCard();

        boolean settled = usingPoints ? fidelityCard.getPoints() >= total : fidelityCard.getBalance() >= total;

        if (settled) {
            if (usingPoints) {
                fidelityCard.setPoints(fidelityCard.getPoints() - total);
            } else {
                fidelityCard.setBalance(fidelityCard.getBalance() - total);
                fidelityCard.setPoints(fidelityCard.getPoints() + total);
            }
        }
        this.fidelityCardRepository.save(fidelityCard);

        return settled;
    }
}
