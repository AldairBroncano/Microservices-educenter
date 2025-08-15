package com.educenter.auth_service.service;

import com.educenter.auth_service.dto.AuthRegisterDTO;
import com.educenter.auth_service.dto.UserProfileDTO;
import com.educenter.auth_service.entity.Auth;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface AuthService {

    Auth saveUser(Auth auth);
    Optional<Auth> getUserByEmail(String email);
    Optional<Auth> getUserById(Long id);


    void registrar(AuthRegisterDTO dto);

    UserProfileDTO getUserProfileById(Long id);


}
