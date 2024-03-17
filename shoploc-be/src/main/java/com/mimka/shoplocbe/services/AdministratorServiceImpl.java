package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.configurations.CustomUserDetails;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.repositories.AdministratorRepository;
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
public class AdministratorServiceImpl implements AdministratorService, UserDetailsService {

    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    private UserDetails getUserDetails(String username) {
        User user = this.getAdministratorByUsername(username);

        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                authorities,
                user.getId());
    }

    private Administrator getAdministratorByUsername(String username) {
        Administrator administrator = this.administratorRepository.findByUsername(username);
        if (administrator == null)  throw new BadCredentialsException("Aucun client n'est associé à ce nom d'utilisateur.");

        return administrator;
    }
}
