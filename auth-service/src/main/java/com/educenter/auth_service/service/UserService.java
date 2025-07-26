package com.educenter.auth_service.service;

import com.educenter.auth_service.entity.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);

}
