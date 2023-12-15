package com.mimka.shoplocbe.configuration;

import com.mimka.shoplocbe.service.UserServiceImpl;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserServiceImpl userServiceImpl;

    // This secret is used to generate JWT tokens & also to decode them.
    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // The session will be handled in the client side.
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CRSF must be disabled with STATELESS session
                //.csrf(crsf-> crsf.disable())
                // These requests do not need authentication.
                .authorizeHttpRequests(requests -> requests.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console"),
                        AntPathRequestMatcher.antMatcher("/authentication/**"),
                        AntPathRequestMatcher.antMatcher("/registration/**")).permitAll())
                // All the others requests must be authenticated.
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                // With JWT authentication, we authenticate the user only once, and the JWT remains associated with the user until its authentication expiration date is reached.
                .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder ( ) {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public JwtEncoder jwtEncoder ( ) {
        // Symmetric algorithm used to encode the tokens.
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }

    @Bean
    public JwtDecoder jwtDecoder ( ) {
        // Symmetric algorithm used to decode the tokens.
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    public AuthenticationManager authenticationManager () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        // The UserDetailsService used to retrieve the user information for authentication.
        daoAuthenticationProvider.setUserDetailsService(userServiceImpl);
        return new ProviderManager(daoAuthenticationProvider);
    }
}