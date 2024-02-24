package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerIT  extends  ControllerIT {

    @Test
    @Transactional
    @Rollback
    public void testRegisterCustomer_WithValidDTO_ReturnCreated () throws Exception {
        mockMvc.perform(post("/authentication/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(getCustomerDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").exists())
                .andExpect(jsonPath("$.username").value("Aziz"));
    }

    @Test
    @Transactional
    @Rollback
    public void testRegisterMerchant_WithValidDTO_ReturnCreated () throws Exception {
        mockMvc.perform(post("/authentication/merchant/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getMerchantDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").exists())
                .andExpect(jsonPath("$.username").value("Aziz"));
    }

    @Test
    @Transactional
    @Rollback
    public void testRegisterAdministrator_WithValidDTO_ReturnCreated () throws Exception {
        mockMvc.perform(post("/authentication/administrator/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getAdministratorDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").exists())
                .andExpect(jsonPath("$.username").value("Aziz"));
    }

    // Methods
    @NotNull
    private AdministratorDTO getAdministratorDTO () {
        AdministratorDTO administratorDTO = new AdministratorDTO();

        administratorDTO.setUsername("Aziz");
        administratorDTO.setLastname("Aziz");
        administratorDTO.setFirstname("BOURAHMA");
        administratorDTO.setPassword("123456789");
        administratorDTO.setConfirmedPassword("123456789");
        administratorDTO.setEmail("az.az2012221@gmail.com");
        administratorDTO.setPhoneNumber("0691797369");

        return administratorDTO;
    }

    @NotNull
    private MerchantDTO getMerchantDTO () {
        MerchantDTO merchantDTO = new MerchantDTO();

        merchantDTO.setUsername("Aziz");
        merchantDTO.setLastname("Aziz");
        merchantDTO.setFirstname("BOURAHMA");
        merchantDTO.setPassword("123456789");
        merchantDTO.setConfirmedPassword("123456789");
        merchantDTO.setEmail("az.az2012221@gmail.com");
        merchantDTO.setPhoneNumber("0691797369");
        merchantDTO.setCommerceId(1L);


        return merchantDTO;
    }

    @NotNull
    private CustomerDTO getCustomerDTO () {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setUsername("Aziz");
        customerDTO.setLastname("Aziz");
        customerDTO.setFirstname("BOURAHMA");
        customerDTO.setPassword("123456789");
        customerDTO.setConfirmedPassword("123456789");
        customerDTO.setEmail("az.az2012221@gmail.com");
        customerDTO.setPhoneNumber("0691797369");

        return customerDTO;
    }
}
