package com.educenter.user_service.service;

import com.educenter.user_service.dto.UserFullProfileDTO;
import com.educenter.user_service.dto.UserProfileDTO;
import com.educenter.user_service.entity.User;
import com.educenter.user_service.feign.AuthFeignClient;
import com.educenter.user_service.repository.UserRepository;

import com.educenter.user_service.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AuthFeignClient authFeignClient;

    private final JwtProvider jwtProvider;



    public void ejemplo(String token) {
        Long userId = jwtProvider.extractUserId(token);
        System.out.println("ID extraído: " + userId);
    }

@Transactional
public User guardar(User user){
    System.out.println("LastName recibido en Service: " + user.getLastName());
    return repository.save(user);
}

public Optional<User> buscarPorId(Long id){
    return repository.findById(id);
}

/*
public Optional<User> buscarPorEmail(String email){
    return repository.findByEmail(email);
}
*/

public UserProfileDTO obternerPerfilDesdeAuth(Long id){
    return authFeignClient.getUserById(id);
}




    public List<UserFullProfileDTO> getAllFullProfiles() {
        return repository.findAll().stream()
                .map(userInfo -> {
                    UserProfileDTO authData = null;

                    try {
                        authData = authFeignClient.getUserById(userInfo.getId());
                    } catch (Exception e) {
                        System.out.println("No se pudo obtener datos desde Auth para ID: " + userInfo.getId());
                    }

                    UserFullProfileDTO dto = new UserFullProfileDTO();
                    dto.setId(userInfo.getId());
                    dto.setUsername(authData != null ? authData.getUsername() : null);
                    dto.setEmail(authData != null ? authData.getEmail() : null);
                    // dto.setRole(authData != null ? authData.getRole() : null);
                    dto.setName(userInfo.getName());
                    dto.setLastName(userInfo.getLastName());
                    dto.setFechaNacimiento(userInfo.getFechaNacimiento());
                    dto.setPhone(userInfo.getPhone());
                    dto.setProfilePhone(userInfo.getProfilePhone());

                    return dto;
                })
                .toList();
    }



}
