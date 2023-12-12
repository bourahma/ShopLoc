package com.mimka.shoplocbe.entity.unit;

import com.mimka.shoplocbe.entity.RegistrationToken;
import com.mimka.shoplocbe.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTokenTest {
    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long userId = 1L;
        String uuid = "some-uuid";
        User user = new User();

        // Act
        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setUserId(userId);
        registrationToken.setUuid(uuid);
        registrationToken.setUser(user);

        // Assert
        assertEquals(userId, registrationToken.getUserId());
        assertEquals(uuid, registrationToken.getUuid());
        assertEquals(user, registrationToken.getUser());
    }
}
