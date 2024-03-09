package com.mimka.shoplocbe.configurations;

import com.mimka.shoplocbe.services.*;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MerchantServiceImpl merchantServiceImpl;

    private final CustomerServiceImpl customerServiceImpl;

    private final AdministratorServiceImpl administratorServiceImpl;

    // This secret is used to generate JWT tokens & also to decode them.
    @Value("${jwt.secret}")
    private String secretKey;

    public SecurityConfig(MerchantServiceImpl merchantServiceImpl, CustomerServiceImpl customerServiceImpl, AdministratorServiceImpl administratorServiceImpl) {
        this.merchantServiceImpl = merchantServiceImpl;
        this.customerServiceImpl = customerServiceImpl;
        this.administratorServiceImpl = administratorServiceImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // The session will be handled in the client side.
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CRSF must be disabled with STATELESS session
                .csrf(crsf-> crsf.disable())
                // These requests do not need authentication.
                .authorizeHttpRequests(requests -> requests.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console"),
                        AntPathRequestMatcher.antMatcher("/authentication/**"),
                                AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
                        AntPathRequestMatcher.antMatcher("/registration/**")).permitAll())
                // All the others requests must be authenticated.
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                // With JWT authentication, we authenticate the user only once, and the JWT remains associated with the user until its authentication expiration date is reached.
                .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
                .cors(Customizer.withDefaults())
                .build();
    }

     @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // you can customize this to specific origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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

    @Bean(name = "customerAuthenticationManager")
    @Primary
    public AuthenticationManager authenticationCustomerManager () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        // The UserDetailsService used to retrieve the customer information for authentication.
        daoAuthenticationProvider.setUserDetailsService(customerServiceImpl);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean(name = "administratorAuthenticationManager")
    public AuthenticationManager authenticationAdministratorManager () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        // The UserDetailsService used to retrieve the merchant information for authentication.
        daoAuthenticationProvider.setUserDetailsService(administratorServiceImpl);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean(name = "merchantAuthenticationManager")
    public AuthenticationManager authenticationMerchantManager () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        // The UserDetailsService used to retrieve the administrator information for authentication.
        daoAuthenticationProvider.setUserDetailsService(merchantServiceImpl);
        return new ProviderManager(daoAuthenticationProvider);
    }
}