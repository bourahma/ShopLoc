package com.mimka.shoplocbe.service.unit;

import com.mimka.shoplocbe.entity.RegistrationToken;
import com.mimka.shoplocbe.entity.User;
import com.mimka.shoplocbe.repository.RegistrationTokenRepository;
import com.mimka.shoplocbe.service.RegistrationTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TokenRegistrationImplTest {

    @Mock
    private RegistrationTokenRepository registrationTokenRepository;

    @InjectMocks
    private RegistrationTokenServiceImpl registrationTokenService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getByUuid_shouldCallTheUserRepository() {
        when(registrationTokenRepository.findByUuid("uuid")).thenReturn(new RegistrationToken());
        registrationTokenService.getByUuid("uuid");
        verify(registrationTokenRepository).findByUuid("uuid");
    }

    @Test
    void resetVerificationToken_shouldCallTheUserRepository () {
        User user = new User();
        registrationTokenService.resetVerificationToken("uuid",user);
        verify(registrationTokenRepository).updateToken("uuid", user);
    }

    @Test
    void deleteVerificationToken_shouldCallTheUserRepository () {
        registrationTokenService.deleteVerificationToken("uuid");
        verify(registrationTokenRepository).deleteByUuid("uuid");
    }
}
