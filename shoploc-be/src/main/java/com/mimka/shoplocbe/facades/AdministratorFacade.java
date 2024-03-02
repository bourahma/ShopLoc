package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.exception.RegistrationException;

public interface AdministratorFacade {
    AdministratorDTO registerAdministrator(AdministratorDTO administratorDTO) throws RegistrationException;
}
