package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.entity.Role;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.repository.RoleRepository;
import com.mimka.shoplocbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    
    private UserRepository userRepository;

    private UserDTOUtil userDTOUtil;

    private RoleRepository roleRepository;

    private String invalidUsernameMessage = "Nom d\'utilisateur incorrect.";

    private String invalidEmailMessage = "Adresse e-mail incorrecte.";

    private String registrationEmailMessage = "L'adresse e-mail est déjà associée à un compte existant.";

    private String registrationUsernameMessage = "Le nom d'utilisateur est déjà utilisé.";

    @Autowired
    public UserServiceImpl (UserRepository userRepository, UserDTOUtil userDTOUtil, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userDTOUtil = userDTOUtil;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new BadCredentialsException(invalidEmailMessage);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null)  throw new BadCredentialsException (invalidUsernameMessage);
        return user;
    }


    @Override
    public User createUser(RegisterDTO registerDTO) throws RegistrationException {
        return this.addUser(registerDTO, this.userRoles());
    }
    private UserDetails getUserDetails(String username) {
        User user = this.getUserByUsername(username);

        Set<Role> roles = user.getRoles();

        Set<GrantedAuthority>  authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                authorities);
    }

    public User addUser(RegisterDTO registerDTO, Set<Role> roles) throws RegistrationException {
        this.emailAndUsernameNotUsedYet(registerDTO);
        User user = this.userDTOUtil.toUser(registerDTO);
        // Add user authorities.
        user.setRoles(roles);
        // Disable the user until he confirms his registration.
        user.setEnabled(true);
        // User is saved.
        this.userRepository.save(user);
        return user;
    }

    public boolean emailAndUsernameNotUsedYet (RegisterDTO registerDTO) throws RegistrationException {
        User user = this.userRepository.findByEmail(registerDTO.getEmail());
        if (user != null) {
            throw new RegistrationException(registrationEmailMessage);
        }
        user = this.userRepository.findByUsername(registerDTO.getUsername());
        if (user != null) {
            throw new RegistrationException(registrationUsernameMessage);
        }
        return true;
    }

    public Set<Role> userRoles () {
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByRoleId(1L));
        return roles;
    }

    public Set<Role> orgaRoles () {
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByRoleId(2L));
        return roles;
    }

    public Set<Role> amdinRoles () {
        return this.roleRepository.findAll().stream().collect(Collectors.toSet());
    }
}