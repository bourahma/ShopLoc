package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Token;
import com.mimka.shoplocbe.repositories.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token getTokenByCustomer(Customer customer) {
        return this.tokenRepository.findTokenByCustomer(customer);
    }

    @Override
    public Token createToken(Customer customer) {
        String uuid = UUID.randomUUID().toString();

        Token token = new Token();

        token.setUuid(uuid);
        token.setCustomer(customer);

        this.tokenRepository.save(token);

        return token;
    }
}
