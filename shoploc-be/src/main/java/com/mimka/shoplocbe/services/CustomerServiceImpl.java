package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.vfp.VfpDTO;
import com.mimka.shoplocbe.dto.vfp.VfpDTOUtil;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.exception.RegistrationTokenInvalidException;
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

import java.time.LocalDate;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    private final CustomerRepository customerRepository;

    private final DtoUtil dtoUtil;

    private final RoleRepository roleRepository;

    private final TokenRepository tokenRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, DtoUtil dtoUtil, RoleRepository roleRepository, TokenRepository tokenRepository) {
        this.customerRepository = customerRepository;
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
        if (customer == null)  throw new BadCredentialsException("Aucun client n'est associé à ce nom d'utilisateur.");

        return customer;
    }

    private UserDetails getUserDetails(String username) {
        User user = this.getCustomerByUsername(username);

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

    public boolean emailAndUsernameUniquenessValid(String email, String username) throws RegistrationException {
        if (isEmailAlreadyRegistered(email)) {
            throw new RegistrationException("L'adresse e-mail est déjà utilisée. Veuillez en choisir une autre.");
        }
        if (isUsernameAlreadyRegistered(username)) {
            throw new RegistrationException("Le nom d'utilisateur est déjà pris. Veuillez en choisir un autre.");
        }
        return true;
    }

    private boolean isEmailAlreadyRegistered(String email) {
        return this.customerRepository.findByEmail(email) != null;
    }

    private boolean isUsernameAlreadyRegistered(String username) {
        return this.customerRepository.findByUsername(username) != null;
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO, FidelityCard fidelityCard) throws RegistrationException {
        Customer customer = this.dtoUtil.toCustomer(customerDTO);
        if (this.emailAndUsernameUniquenessValid(customerDTO.getEmail(), customerDTO.getUsername())) {
            customer.setRole(this.roleRepository.findByRoleId(1L));
            customer.setEnabled(false);
            customer.setSubscriptionDate(LocalDate.now());
            this.customerRepository.save(customer);
        }

        return customer;
    }

    public Customer enableCustomer (String uuid) throws RegistrationTokenInvalidException {
        Token token = this.tokenRepository.findTokenByUuid(uuid);
        if (token == null) {
            throw new RegistrationTokenInvalidException("Le token founi n'est pas valide");
        }
        Customer customer = token.getCustomer();
        customer.setEnabled(true);
        this.customerRepository.save(customer);
        this.tokenRepository.delete(token);

        return customer;
    }
}
