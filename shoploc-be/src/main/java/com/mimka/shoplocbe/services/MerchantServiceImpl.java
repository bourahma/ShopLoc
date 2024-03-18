package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Merchant;
import com.mimka.shoplocbe.entities.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.repositories.MerchantRepository;
import com.mimka.shoplocbe.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public class MerchantServiceImpl implements MerchantService, UserDetailsService {

    private final MerchantRepository merchantRepository;

    private final RoleRepository roleRepository;

    private final DtoUtil dtoUtil;

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository, RoleRepository roleRepository, DtoUtil dtoUtil) {
        this.merchantRepository = merchantRepository;
        this.roleRepository = roleRepository;
        this.dtoUtil = dtoUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    private UserDetails getUserDetails(String username) {
        User user = this.merchantRepository.findByUsername(username);
        if (user == null)  throw new BadCredentialsException ("Aucun commerçant n'est associé à ce nom d'utilisateur.");

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
        return this.merchantRepository.findByEmail(email) != null;
    }

    private boolean isUsernameAlreadyRegistered(String username) {
        return this.merchantRepository.findByUsername(username) != null;
    }

    @Override
    public Merchant createMerchant(MerchantDTO merchantDTO, Commerce commerce) throws RegistrationException {
        Merchant merchant = this.dtoUtil.toMerchant(merchantDTO);
        if (this.emailAndUsernameUniquenessValid(merchantDTO.getEmail(), merchantDTO.getUsername())) {
            // Add merchant authorities.
            merchant.setRole(this.roleRepository.findByRoleId(3L));
            merchant.setEnabled(true);
            merchant.setCommerce(commerce);
            merchant.setSubscriptionDate(LocalDate.now());
            // Merchant is saved.
            this.merchantRepository.save(merchant);
        }
        return merchant;
    }
}
