package com.educenter.user_service.service;

import com.educenter.user_service.dto.UserFullProfileDTO;
import com.educenter.user_service.dto.UserProfileDTO;
import com.educenter.user_service.entity.User;
import com.educenter.user_service.feign.AuthFeignClient;
import com.educenter.user_service.repository.UserRepository;

import com.educenter.user_service.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


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
        List<User> users = repository.findAll();

        return users.stream().map(user -> {
            // Llamada a auth-service
            UserProfileDTO authData = authFeignClient.getUserById(user.getId());

            // Combinar datos
            UserFullProfileDTO fullProfile = new UserFullProfileDTO();
            fullProfile.setId(user.getId());
            fullProfile.setUsername(authData.getUsername());
            fullProfile.setEmail(authData.getEmail());
            fullProfile.setRole(authData.getRole());

            fullProfile.setName(user.getName());
            fullProfile.setLastName(user.getLastName());
            fullProfile.setFechaNacimiento(user.getFechaNacimiento());
            fullProfile.setPhone(user.getPhone());
            fullProfile.setProfilePhone(user.getProfilePhone());

            return fullProfile;
        }).collect(Collectors.toList());
    }





    public UserFullProfileDTO getFullProfileById(Long id) {
        // Buscar en la base de user-service
        User user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado en user-service"));

        // Llamada a auth-service
        UserProfileDTO authData = authFeignClient.getUserById(id);

        // Combinar datos
        UserFullProfileDTO fullProfile = new UserFullProfileDTO();
        fullProfile.setId(user.getId());
        fullProfile.setUsername(authData.getUsername());
        fullProfile.setEmail(authData.getEmail());
        fullProfile.setRole(authData.getRole());

        fullProfile.setName(user.getName());
        fullProfile.setLastName(user.getLastName());
        fullProfile.setFechaNacimiento(user.getFechaNacimiento());
        fullProfile.setPhone(user.getPhone());
        fullProfile.setProfilePhone(user.getProfilePhone());

        return fullProfile;
    }






}
