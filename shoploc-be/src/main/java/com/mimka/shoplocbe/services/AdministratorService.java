package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.entities.Administrator;
import com.mimka.shoplocbe.exception.RegistrationException;

public interface AdministratorService {

    Administrator createAdministrator(AdministratorDTO administratorDTO) throws RegistrationException;

}
