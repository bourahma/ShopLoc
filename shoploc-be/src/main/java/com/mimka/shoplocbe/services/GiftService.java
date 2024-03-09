package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.GiftHistory;
import com.mimka.shoplocbe.entities.Product;

import java.util.List;

public interface GiftService {

    List<GiftHistory> getCustomerGiftsHistory (Customer customer);

    GiftHistory availGift(Customer customer, Product product);
}
