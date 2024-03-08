package com.mimka.shoplocbe.controllerIT;

import com.mimka.shoplocbe.dto.user.AdministratorDTO;
import com.mimka.shoplocbe.dto.user.AuthDTO;
import com.mimka.shoplocbe.dto.user.CustomerDTO;
import com.mimka.shoplocbe.dto.user.MerchantDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegistrationControllerIT  extends  ControllerIT {

    @Test
    @Transactional
    @Rollback
    void testRegisterCustomer_WithValidDTO_ReturnCreated () throws Exception {
        mockMvc.perform(post("/authentication/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getCustomerDTO())))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.role").exists())
                        .andExpect(jsonPath("$.username").value("Aziz"));
    }

    @Test
    @Transactional
    @Rollback
    void testRegisterCustomer_WithAnEmailAlreadyUsed_ReturnConflict () throws Exception {
        CustomerDTO customerDTO = this.getCustomerDTO();
        customerDTO.setEmail("az.az201221@gmail.com");

        mockMvc.perform(post("/authentication/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(customerDTO)))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.message").value("L'adresse e-mail est déjà utilisée. Veuillez en choisir une autre."));
    }

    @Test
    @Transactional
    @Rollback
    void testRegisterCustomer_WithAUsernameAlreadyUsed_ReturnConflict () throws Exception {
        CustomerDTO customerDTO = this.getCustomerDTO();
        customerDTO.setUsername("Joe");

        mockMvc.perform(post("/authentication/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(customerDTO)))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.message").value("Le nom d'utilisateur est déjà pris. Veuillez en choisir un autre."));
    }

    @Test
    @Transactional
    @Rollback
    void testRegisterMerchant_WithValidDTO_ReturnCreated () throws Exception {
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
    void testRegisterAdministrator_WithValidDTO_ReturnCreated () throws Exception {
        mockMvc.perform(post("/authentication/administrator/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getAdministratorDTO())))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.role").exists())
                        .andExpect(jsonPath("$.username").value("Aziz"));
    }

    @Test
    @Transactional
    @Rollback
    void testValidateRegister_WithInvalidUuidDoNotEnableCustomer_ReturnCreated () throws Exception {
        mockMvc.perform(post("/authentication/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getCustomerDTO())))
                        .andExpect(status().isCreated());

        mockMvc.perform(get("/authentication/customer/register/123e4567-e89b-12d3-a456-426614174000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getCustomerDTO())))
                        .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/customer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getAuthenticationDTO())))
                        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Transactional
    @Rollback
    void testValidateRegister_WithValidUuidDoEnableCustomer_ReturnCreated () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/customer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getAuthenticationDTO())))
                        .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        mockMvc.perform(get("/authentication/customer/register/123e4567-a456-426614174000-e89b-12d3"))
                        .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/customer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(getAuthenticationDTO())))
                        .andExpect(MockMvcResultMatchers.status().isOk());
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

    @NotNull
    private AuthDTO getAuthenticationDTO () {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("Mohammed");
        authDTO.setPassword("12345678");

        return authDTO;
    }
}
