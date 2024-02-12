package com.mimka.shoplocbe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager customerAuthenticationManager;
    private final AuthenticationManager administratorAuthenticationManager;
    private final AuthenticationManager merchantAuthenticationManager;

    @Autowired
    public AuthenticationServiceImpl(JwtEncoder jwtEncoder, @Qualifier("customerAuthenticationManager") AuthenticationManager customerAuthenticationManager,
                                     @Qualifier("administratorAuthenticationManager") AuthenticationManager administratorAuthenticationManager,
                                     @Qualifier("merchantAuthenticationManager") AuthenticationManager merchantAuthenticationManager) {
        this.jwtEncoder = jwtEncoder;
        this.customerAuthenticationManager = customerAuthenticationManager;
        this.administratorAuthenticationManager = administratorAuthenticationManager;
        this.merchantAuthenticationManager = merchantAuthenticationManager;
    }
    public Map<String, String> loginUserWithUsername (AuthenticationManager authenticationManager, String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        // Once the user is authenticated successfully, JWT will be generated.
        Instant instant =  Instant.now();
        // Retrieves the user's roles. The roles are constructed in a single string & separated by a space.
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        // Set JWT claims
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                //  Date when the JWT is generated.
                .issuedAt(instant)
                .expiresAt(instant.plus(120, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", scope)
                .build();

        // Sign the JWT token using MacAlgorithm HS512.
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet
        );

        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        return Map.of("access-token", jwt, "role", scope);
    }

    @Override
    public Map<String, String> loginCustomerWithUsername(String username, String password) {
        return this.loginUserWithUsername(customerAuthenticationManager, username, password);
    }

    @Override
    public Map<String, String> loginAdministratorWithUsername(String username, String password) {
        return this.loginUserWithUsername(administratorAuthenticationManager, username, password);
    }

    @Override
    public Map<String, String> loginMerchantWithUsername(String username, String password) {
        return this.loginUserWithUsername(merchantAuthenticationManager, username, password);
    }
}
