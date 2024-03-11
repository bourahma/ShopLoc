package com.mimka.shoplocbe.controllerIT;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VfpControllerIT extends ControllerIT {

    @Test
    void testGetCustomerVfp_ReturnOK () throws Exception {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String expectedGrantedDate = currentDate.minusDays(30).format(formatter);
        String expectedExpirationDate = currentDate.minusDays(23).format(formatter);

        mockMvc.perform(get("/vfp/")
                        .header("Authorization", "Bearer " + customerJWTToken))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.vfpMember").value(true))
                        .andExpect(jsonPath("$.grantedDate").value(expectedGrantedDate))
                        .andExpect(jsonPath("$.expirationDate").value(expectedExpirationDate));
    }

    @Test
    void testGetCustomerVfpHistory_ReturnOK () throws Exception {
        mockMvc.perform(get("/vfp/history")
                        .header("Authorization", "Bearer " + customerJWTToken))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetCustomerVfpHistory_WhenNeverVFPStatusNeverEarned_ReturnOK () throws Exception {
        mockMvc.perform(get("/vfp/history")
                        .header("Authorization", "Bearer " + notVFPCustomerJWTToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}