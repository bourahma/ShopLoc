package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.user.RegisterDTO;
import com.mimka.shoplocbe.dto.user.UserDTO;
import com.mimka.shoplocbe.dto.user.UserDTOUtil;
import com.mimka.shoplocbe.entity.Role;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.exception.EmailAlreadyUsedException;
import com.mimka.shoplocbe.exception.HandleMailSendException;
import com.mimka.shoplocbe.exception.UsernameExistsException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public User getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user == null)  throw new BadCredentialsException("E-mail do not match any user.");
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null)  throw new UsernameNotFoundException("Username do not match any user.");
        return user;
    }


    @Override
    public User createUser(RegisterDTO registerDTO) throws EmailAlreadyUsedException, UsernameExistsException {
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

    public User addUser(RegisterDTO registerDTO, Set<Role> roles) throws EmailAlreadyUsedException, UsernameExistsException {
        this.emailAndUsernameNotUsedYet(registerDTO);
        this.noRegistrationInitiatedYet(registerDTO);
        User user = this.userDTOUtil.toUser(registerDTO);
        // Add user authorities.
        user.setRoles(roles);
        // Disable the user until he confirms his registration.
        user.setEnabled(false);
        // User is saved.
        this.userRepository.save(user);
        return user;
    }

    public boolean emailAndUsernameNotUsedYet (RegisterDTO registerDTO) throws EmailAlreadyUsedException, UsernameExistsException {
        User user = this.userRepository.findByEmail(registerDTO.getEmail());
        if (user != null) {
            throw new EmailAlreadyUsedException("E-mail already used.");
        }
        user = this.userRepository.findByUsername(registerDTO.getUsername());
        if (user != null) {
            throw new UsernameExistsException("Username already used.");
        }
        return true;
    }

    public boolean noRegistrationInitiatedYet (RegisterDTO registerDTO) {
        User user = this.userRepository.findByEmail(registerDTO.getEmail());
        if (user != null && !user.getEnabled()) {
            try {
                this.emailService.resendVerificationEmail(user);
                return false;
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (HandleMailSendException e) {
                throw new RuntimeException(e);
            }
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
        Set<Role> roles = this.roleRepository.findAll().stream().collect(Collectors.toSet());
        return roles;
    }
}