package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.GiftHistory;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.repositories.GiftHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {

    private final GiftHistoryRepository giftHistoryRepository;

    @Autowired
    public GiftServiceImpl(GiftHistoryRepository giftHistoryRepository) {
        this.giftHistoryRepository = giftHistoryRepository;
    }

    @Override
    public List<GiftHistory> getCustomerGiftsHistory(Customer customer) {
        return this.giftHistoryRepository.findByCustomer(customer);
    }

    @Override
    public GiftHistory availGift(Customer customer, Product product) {
        GiftHistory giftHistory = new GiftHistory();
        giftHistory.setProduct(product);
        giftHistory.setCustomer(customer);
        giftHistory.setPurchaseDate(LocalDate.now());

        return this.giftHistoryRepository.save(giftHistory);
    }
}
