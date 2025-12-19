package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {

    User registerUser(String fullName, String email, String password);

    User getByEmail(String email);

    boolean exists(String email);
}
