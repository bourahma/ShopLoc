package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.entities.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.repositories.MerchantRepository;
import com.mimka.shoplocbe.repositories.RoleRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class MerchantServiceImpl implements MerchantService, UserDetailsService {

    private final MerchantRepository merchantRepository;

    private final RoleRepository roleRepository;

    private final DtoUtil dtoUtil;

    private final MailServiceImpl mailService;

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository, RoleRepository roleRepository, DtoUtil dtoUtil, MailServiceImpl mailService) {
        this.merchantRepository = merchantRepository;
        this.roleRepository = roleRepository;
        this.dtoUtil = dtoUtil;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    private UserDetails getUserDetails(String username) {
        User user = this.merchantRepository.findByUsername(username);
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
        if (this.merchantRepository.findByEmail(email) != null) {
            throw new RegistrationException("registrationEmailMessage");
        }
        if (this.merchantRepository.findByUsername(password) != null) {
            throw new RegistrationException("registrationUsernameMessage");
        }
        return true;
    }


    @Override
    public Merchant getMerchantByUsername(String username) {
        Merchant merchant = this.merchantRepository.findByUsername(username);
        if (merchant == null)  throw new BadCredentialsException("invalidEmailMessage");
        return merchant;
    }

    @Override
    public Merchant createMerchant(MerchantDTO merchantDTO) throws RegistrationException {
        Merchant merchant = this.dtoUtil.toMerchant(merchantDTO);
        if (this.emailAndUsernameUniquenessValid(merchantDTO.getEmail(), merchantDTO.getUsername())) {
            // Add merchant authorities.
            merchant.setRole(this.roleRepository.findByRoleId(1L));
            merchant.setEnabled(true);
            // Merchant is saved.
            /*
            merchant.setCommerce(this.commerceRepository.findByCommerceId(merchantDTO.getCommerceId()));

             */
            this.merchantRepository.save(merchant);
        }

        return merchant;
    }
}
