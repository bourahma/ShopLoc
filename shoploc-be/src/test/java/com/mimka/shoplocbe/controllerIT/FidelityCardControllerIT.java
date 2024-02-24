package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.fidelityCard.CreditBalanceDTO;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FidelityCardControllerIT extends AuthenticationControllerIT {
    @Test
    public void testGetCustomerFidelityCard_ReturnOK () throws Exception {
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.fidelityCardId").value("123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(jsonPath("$.points").value(54.50))
                .andExpect(jsonPath("$.balance").value(49.5));
    }

    @Test
    public void testCreditCustomerFidelityCard_ReturnOK () throws Exception {
        CreditBalanceDTO creditBalanceDTO = this.getCreditBalanceDTO();

        mockMvc.perform(post("/fidelity-card/credit")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(creditBalanceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.fidelityCardId").value("123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(jsonPath("$.points").value(54.50))
                .andExpect(jsonPath("$.balance").value(69.5));
    }

    @Test
    public void testGetCreditedTransactionsHistory_ReturnOK () throws Exception {
        mockMvc.perform(get("/fidelity-card/history-balance/credits/123e4567-e89b-12d3-a456-426614174000")
                        .header("Authorization", "Bearer " + customerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.[0].type").value("CREDIT"))
                .andExpect(jsonPath("$.[0].amount").value(greaterThan(0.0)))
                .andExpect(jsonPath("$.[0].amount").value(lessThanOrEqualTo(50.00)))
                .andExpect(jsonPath("$.[1].type").value("CREDIT"))
                .andExpect(jsonPath("$.[1].amount").value(greaterThan(0.0)))
                .andExpect(jsonPath("$.[1].amount").value(lessThanOrEqualTo(50.00)))
                .andExpect(jsonPath("$.[2].type").value("CREDIT"))
                .andExpect(jsonPath("$.[2].amount").value(greaterThan(0.0)))
                .andExpect(jsonPath("$.[2].amount").value(lessThanOrEqualTo(50.00)));
    }

    // TODO : Add test for debited transactions history.

    // TODO : Add tests for earned and spent points transactions history. type is : SPENT or EARNED

    @NotNull
    private CreditBalanceDTO getCreditBalanceDTO () {
        CreditBalanceDTO creditBalanceDTO = new CreditBalanceDTO ();
        creditBalanceDTO.setFidelityCardId("123e4567-e89b-12d3-a456-426614174000");
        creditBalanceDTO.setAmount(20);

        return creditBalanceDTO;
    }

}
