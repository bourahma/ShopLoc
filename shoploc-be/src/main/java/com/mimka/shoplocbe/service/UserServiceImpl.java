package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.entity.Role;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.exception.HandleMailSendException;
import com.mimka.shoplocbe.repository.RoleRepository;
import com.mimka.shoplocbe.repository.UserRepository;
import jakarta.mail.MessagingException;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTOUtil userDTOUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user == null)  throw new BadCredentialsException("E-mail do not match any user.");
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null)  throw new BadCredentialsException("Username do not match any user.");
        return this.userRepository.findByUsername(username);
    }


    @Override
    public User createUser(RegisterDTO registerDTO) throws EmailAlreadyUsedException {
        return this.createUser(registerDTO, this.userRoles());
    }

    @Override
    public User createOrga(UserDTO userDTO) {
        return null;
    }

    // Privates methods.

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

    private User createUser(RegisterDTO userDTO, Set<Role> roles) throws EmailAlreadyUsedException {
        this.checkEmail(userDTO.getEmail());
        User user = this.userDTOUtil.toUser(userDTO);
        // Add user authorities.
        user.setRoles(roles);
        // Disable the user until he confirms his registration.
        user.setEnabled(false);
        // User is saved first.
        this.userRepository.save(user);

        return user;
    }

    private void checkEmail (String email) throws EmailAlreadyUsedException {
        // Check if the e-mail is already used for another account.
        User user = this.userRepository.findByEmail(email);
        if (user != null && user.getEnabled()) {
            throw new EmailAlreadyUsedException("E-mail already used.");
        } else if (user != null && !user.getEnabled()) {
            try {
                this.emailService.resendVerificationEmail(user);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (HandleMailSendException e) {
                throw new RuntimeException(e);
            }
            throw new EmailAlreadyUsedException("A registration initiated. Please validate your registration.");

        }
    }

    private Set<Role> userRoles () {
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByRoleId(1L));
        return roles;
    }

    private Set<Role> orgaRoles () {
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByRoleId(1L));
        roles.add(this.roleRepository.findByRoleId(2L));
        return roles;
    }

    private Set<Role> amdinRoles () {
        Set<Role> roles = this.roleRepository.findAll().stream().collect(Collectors.toSet());
        return roles;
    }

}