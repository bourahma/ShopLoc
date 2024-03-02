package com.mimka.shoplocbe.dto.gift;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Product;
import java.util.List;

public class Gift_historyDTO {
   private Long id;
   private String dateAchat;
   private List<Customer> customerId;
   private List<Product> giftId;

}
