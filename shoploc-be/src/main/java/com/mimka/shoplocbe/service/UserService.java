package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> getUsers ();
}
