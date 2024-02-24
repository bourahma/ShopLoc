package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.DtoUtil;
import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.entities.Administrator;
import com.mimka.shoplocbe.exception.RegistrationException;
import com.mimka.shoplocbe.services.AdministratorService;
import org.springframework.stereotype.Component;

@Component
public class AdministratorFacadeImpl implements AdministratorFacade {

    private final AdministratorService administratorService;

    private final DtoUtil dtoUtil;

    public AdministratorFacadeImpl(AdministratorService administratorService, DtoUtil dtoUtil) {
        this.administratorService = administratorService;
        this.dtoUtil = dtoUtil;
    }

    @Override
    public AdministratorDTO registerAdministrator(AdministratorDTO administratorDTO) throws RegistrationException {
        Administrator administrator = this.administratorService.createAdministrator(administratorDTO);

        return this.dtoUtil.toAdministratorDTO(administrator);
    }
}
