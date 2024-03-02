package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.repositories.AdministratorRepository;
import com.mimka.shoplocbe.repositories.RoleRepository;
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

    private final RoleRepository roleRepository;

    private final DtoUtil dtoUtil;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository, RoleRepository roleRepository, DtoUtil dtoUtil) {
        this.administratorRepository = administratorRepository;
        this.roleRepository = roleRepository;
        this.dtoUtil = dtoUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    private UserDetails getUserDetails(String username) {
        User user = this.administratorRepository.findByUsername(username);
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
        if (this.administratorRepository.findByEmail(email) != null) {
            throw new RegistrationException("registrationEmailMessage");
        }
        if (this.administratorRepository.findByUsername(password) != null) {
            throw new RegistrationException("registrationUsernameMessage");
        }
        return true;
    }

    @Override
    public Administrator createAdministrator(AdministratorDTO administratorDTO) throws RegistrationException {
        Administrator administrator = this.dtoUtil.toAdministrator(administratorDTO);
        if (this.emailAndUsernameUniquenessValid(administratorDTO.getEmail(), administratorDTO.getUsername())) {
            // Add administrator authorities.
            administrator.setRole(this.roleRepository.findByRoleId(1L));
            administrator.setEnabled(true);
            // Administrator is saved.
            this.administratorRepository.save(administrator);
        }

        return administrator;
    }
}
