package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BenefitControllerIT extends AuthenticationControllerIT {

    @Test
    void testGetBenefits_ReturnOK () throws Exception {
        mockMvc.perform(get("/benefit/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].benefitId").value(1))
                .andExpect(jsonPath("$.[1].benefitId").value(2))
                .andExpect(jsonPath("$.[2].benefitId").value(3))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Transactional
    @Rollback
    void testAvailBenefit_ReturnOK () throws Exception {
        mockMvc.perform(get("/benefit/history")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mockMvc.perform(get("/benefit/1")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/benefit/2")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/benefit/history")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    @Transactional
    @Rollback
    void testCreateBenefit_WHenCustomerWhenValidForm_ReturnCreated () throws Exception {
        mockMvc.perform(post("/benefit/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getBenefitDTO())))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateBenefit_WHenCustomerWhenInvalidFormFields_ReturnBabRequest () throws Exception {
        BenefitDTO benefitDTO = this.getBenefitDTO();
        benefitDTO.setDescription(null);

        mockMvc.perform(post("/benefit/")
                        .header("Authorization", "Bearer " + administratorJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(benefitDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAvailBenefit_WHenCustomerIsNotVFPMember_ReturnForbidden () throws Exception {
        mockMvc.perform(get("/benefit/1")
                        .header("Authorization", "Bearer " + notVFPCustomerJWTToken))
                .andExpect(status().isForbidden());
    }

    @NotNull
    public BenefitDTO getBenefitDTO () {
        BenefitDTO benefit = new BenefitDTO();

        benefit.setBenefitAvailable(true);
        benefit.setDescription("Emplacement vélo gratuit.");

        return benefit;
    }
}
