package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.entity.RegistrationToken;
import com.mimka.shoplocbe.entity.Role;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.repository.RegistrationTokenRepository;
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
    private RegistrationTokenRepository registrationTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDetails(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByVerificationCode(String uuid) {
        return this.userRepository.findByUuid(uuid);
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
    public User createUser(UserDTO userDTO) throws EmailAlreadyUsedException, MessagingException {
        // Check if the e-mail is already used for another account.
        User user = this.userRepository.findByEmail(userDTO.getEmail());
        if (user != null && user.getEnabled()) {
            throw new EmailAlreadyUsedException("The provided email address already used.");
        } else if (user != null) {
            throw new EmailAlreadyUsedException("An registration with this email already initiated. A new verification email has been sent to activate the account.");
            // to do send new email verification.
        }

        user = this.userDTOUtil.toUser(userDTO);

        // Add user authorities.
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByRoleId(1L));
        user.setRoles(roles);
        // Disable the user until he confirms his registration.
        user.setEnabled(false);
        // User is saved first.
        this.userRepository.save(user);
        // Then secondly it's registration token is saved.
        String uuid = this.setVerificationToken(user);

        return user;
    }

    @Override
    public User createUserOrga(UserDTO userDTO) {
        return null;
    }

    private String setVerificationToken (User user) {
        RegistrationToken registrationToken = new RegistrationToken();

        String uuid = UUID.randomUUID().toString();
        registrationToken.setUuid(uuid);
        registrationToken.setUser(user);

        this.registrationTokenRepository.save(registrationToken);

        return uuid;
    }
}