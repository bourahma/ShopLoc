package com.mimka.shoplocbe.controllerIT;

import com.jayway.jsonpath.JsonPath;
import com.mimka.shoplocbe.dto.order.OrderDTO;
import com.mimka.shoplocbe.dto.order.OrderProductDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerIT extends ControllerIT {

    @Test
    @Transactional
    @Rollback
    void testCreateOrder_WithValidOrderFields_ReturnOK () throws Exception {
        mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commerceId").value(1))
                .andExpect(jsonPath("$.commerceName").value("Boulangerie du Coin"))
                .andExpect(jsonPath("$.orderDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products", hasSize(2)));
    }

    @Test
    @Transactional
    @Rollback
    void testCreateOrder_WithEmptyOrderDto_ReturnBadRequest () throws Exception {
        OrderDTO orderDTO = new OrderDTO();

        mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    void testCreateOrder_FidelityCardPointsAndBalanceNotDebited_ReturnsOk () throws Exception {
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(54.50))
                .andExpect(jsonPath("$.balance").value(49.5));

        mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTO())))
                .andExpect(status().isOk());

        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(54.50))
                .andExpect(jsonPath("$.balance").value(49.5));
    }

    @Test
    @Transactional
    @Rollback
    void testCreateOrder_WithInvalidCommerceId_ReturnBadRequest () throws Exception {
        mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTOWithInvalidCommerceId())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("L'id du commerce est requis et doit être un nombre positif."));
    }

    @Test
    @Transactional
    @Rollback
    void testCreateOrder_WithInvalidProductId_ReturnBadRequest () throws Exception {
        mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTOWithInvalidProductId())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("L'id du produit est requis et doit être un nombre psotif."));
        ;
    }

    @Test
    @Transactional
    @Rollback
    void testCreateOrder_WithInvalidProductQuantity_ReturnBadRequest () throws Exception {
        mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTOWithInvalidProductQuantity())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("La quantité est requise et doit être un nombre positif."));
    }

    @Test
    @Transactional
    @Rollback
    void testCreateOrder_WithEmptyProducts_ReturnBadRequest () throws Exception {
        mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTOWithEmptyProducts())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Un produit est requis pour effectuer une commande."));
    }

    @Test
    @Transactional
    @Rollback
    void testSettleOrderUsingBalance_ReturnOk () throws Exception {
        // Check balance before.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(54.50))
                .andExpect(jsonPath("$.balance").value(49.5));

        // Create an order.
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        // Settle the order using balance
        mockMvc.perform(get("/order/settle/using-balance/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Verify that balance is debited.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(68.5))
                .andExpect(jsonPath("$.balance").value(34.54));
    }

    @Test
    @Transactional
    @Rollback
    void testSettleOrderUsingBalance_ProductsQuantityUpdated_ReturnOk () throws Exception {
        mockMvc.perform(get("/product/detail/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(150));

        mockMvc.perform(get("/product/detail/4")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(200));

        // Create an order.
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        // Settle the order using balance
        mockMvc.perform(get("/order/settle/using-balance/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/product/detail/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(148));

        mockMvc.perform(get("/product/detail/4")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(196));

    }

    @Test
    @Transactional
    @Rollback
    void testProductQuantityUpdatedWhenPromotionOffer_ReturnOk () throws Exception {
        mockMvc.perform(get("/product/detail/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(150));

        // Create an order.
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTOWithOfferPromotion())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        // Settle the order using balance
        mockMvc.perform(get("/order/settle/using-balance/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/product/detail/1")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(142));
    }

    @Test
    @Transactional
    @Rollback
    void testProductQuantityUpdatedWhenDiscountPromotion_ReturnOk () throws Exception {
        mockMvc.perform(get("/product/detail/13")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(50));

        // Create an order.
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTOWithDiscountPromotion())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        // Settle the order using balance
        mockMvc.perform(get("/order/settle/using-balance/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Verify that balance is debited.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(28.715999999999998))
                .andExpect(jsonPath("$.points").value(74.5));

        mockMvc.perform(get("/product/detail/13")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(48));
    }

    @Test
    @Transactional
    @Rollback
    void testSettleOrderUsingPoints_ReturnOk () throws Exception {
        // Check balance before.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(54.50))
                .andExpect(jsonPath("$.balance").value(49.5));

        // Create an order.
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        // Settle the order using balance
        mockMvc.perform(get("/order/settle/using-points/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Verify that points are debited.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(44.5))
                .andExpect(jsonPath("$.balance").value(49.5));
    }

    @Test
    @Transactional
    @Rollback
    void testSettleOrderUsingPoints_WhenTotalOrderIsUpperToCustomerPoints_ReturnBadRequest () throws Exception {
        OrderDTO orderDTO = this.getOrderDTO();
        orderDTO.setProducts(orderDTO.getProducts().stream()
                .map(product -> {
                    product.setQuantity(100);
                    return product;
                })
                .collect(Collectors.toSet()));

        // Check balance before.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(54.50));

        // Create an order.
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        // Settle the order using balance
        mockMvc.perform(get("/order/settle/using-points/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Le solde de points sur votre carte fidélité est insuffisant pour effectuer cette transaction."));


        //Verify that points are not debited.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(54.50));
    }
    @Test
    @Transactional
    @Rollback
    void testSettleOrderUsingPoints_WhenTotalOrderTotalIsUpperToCustomerBalance_ReturnBadRequest () throws Exception {
        OrderDTO orderDTO = this.getOrderDTO();
        orderDTO.setProducts(orderDTO.getProducts().stream()
                .map(product -> {
                    product.setQuantity(200);
                    return product;
                })
                .collect(Collectors.toSet()));

        // Check balance before.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(54.50));

        // Create an order.
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        // Settle the order using balance
        mockMvc.perform(get("/order/settle/using-balance/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //Verify that points is not debited.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(49.5));
    }

    @Test
    @Transactional
    @Rollback
    void testGetQrCodeSettle_ReturnOk () throws Exception {
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        mockMvc.perform(get("/order/qr-code-uuid/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qr-code").exists())
                .andExpect(jsonPath("$.qr-code", matchesPattern("[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}")));;
    }

    @Test
    @Transactional
    @Rollback
    void testSettleOrderUsingBalanceQRCode_ReturnOk () throws Exception {
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        String qRCodeUUID = JsonPath.read(mockMvc.perform(get("/order/qr-code-uuid/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.qr-code");

        mockMvc.perform(get("/order/settle/using-qr-code-balance/" + qRCodeUUID)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Verify that balance is debited.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(34.54));
    }

    @Test
    @Transactional
    @Rollback
    void testSettleOrderUsingPointsQRCode_ReturnOk () throws Exception {
        Integer orderId = JsonPath.read(mockMvc.perform(post("/order/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.getOrderDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.orderId");

        String qRCodeUUID = JsonPath.read(mockMvc.perform(get("/order/qr-code-uuid/" + orderId)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), "$.qr-code");

        mockMvc.perform(get("/order/settle/using-qr-code-points/" + qRCodeUUID)
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Verify that balance is not debited.
        mockMvc.perform(get("/fidelity-card/")
                        .header("Authorization", "Bearer " + customerJWTToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(44.5));
    }

    // Methods
    @NotNull
    private OrderDTO getOrderDTO () {
        OrderProductDTO orderProductDTOPain = new OrderProductDTO();
        orderProductDTOPain.setProductId(1L);
        orderProductDTOPain.setQuantity(2);

        OrderProductDTO orderProductDTOTwoEclair = new OrderProductDTO();
        orderProductDTOTwoEclair.setProductId(4L);
        orderProductDTOTwoEclair.setQuantity(4);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCommerceId(1L);
        orderDTO.setProducts(Set.of(orderProductDTOPain, orderProductDTOTwoEclair));

        return orderDTO;
    }

    @NotNull
    private OrderDTO getOrderDTOWithOfferPromotion () {
        OrderProductDTO orderProductDTOPain = new OrderProductDTO();
        orderProductDTOPain.setProductId(1L);
        orderProductDTOPain.setQuantity(6);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCommerceId(1L);
        orderDTO.setProducts(Set.of(orderProductDTOPain));

        return orderDTO;
    }

    @NotNull
    private OrderDTO getOrderDTOWithDiscountPromotion () {
        OrderProductDTO orderProductDTOPain = new OrderProductDTO();
        orderProductDTOPain.setProductId(13L);
        orderProductDTOPain.setQuantity(2);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCommerceId(1L);
        orderDTO.setProducts(Set.of(orderProductDTOPain));

        return orderDTO;
    }

    @NotNull
    private OrderDTO getOrderDTOWithInvalidCommerceId () {
        OrderProductDTO orderProductDTOPain = new OrderProductDTO();
        orderProductDTOPain.setProductId(1L);
        orderProductDTOPain.setQuantity(2);

        OrderProductDTO orderProductDTOTwoEclair = new OrderProductDTO();
        orderProductDTOTwoEclair.setProductId(4L);
        orderProductDTOTwoEclair.setQuantity(4);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProducts(Set.of(orderProductDTOPain, orderProductDTOTwoEclair));

        return orderDTO;
    }
    @NotNull
    private OrderDTO getOrderDTOWithInvalidProductId () {
        OrderProductDTO orderProductDTOTwoEclair = new OrderProductDTO();
        orderProductDTOTwoEclair.setQuantity(4);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCommerceId(1L);
        orderDTO.setProducts(Set.of(orderProductDTOTwoEclair));

        return orderDTO;
    }

    @NotNull
    private OrderDTO getOrderDTOWithInvalidProductQuantity () {
        OrderProductDTO orderProductDTOTwoEclair = new OrderProductDTO();
        orderProductDTOTwoEclair.setProductId(4L);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCommerceId(1L);
        orderDTO.setProducts(Set.of(orderProductDTOTwoEclair));

        return orderDTO;
    }

    @NotNull
    private OrderDTO getOrderDTOWithEmptyProducts () {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCommerceId(1L);


        return orderDTO;
    }
}
