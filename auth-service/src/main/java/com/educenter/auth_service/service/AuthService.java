package com.educenter.auth_service.service;

import com.educenter.auth_service.dto.*;
import com.educenter.auth_service.entity.Auth;

import java.util.List;
import java.util.Optional;

public interface AuthService {

    Auth saveUser(Auth auth);
    Optional<Auth> getUserByEmail(String email);
    Optional<Auth> getUserById(Long id);


    AuthResponseDTO registrar(AuthRegisterDTO dto);

    UserProfileDTO getUserProfileById(Long id);

    AuthLoginResponseDTO login(AuthLoginDTO dto);

    List<Auth> getUsersProfiles();
}
