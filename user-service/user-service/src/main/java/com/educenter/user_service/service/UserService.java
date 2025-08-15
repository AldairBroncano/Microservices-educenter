package com.educenter.user_service.service;

import com.educenter.user_service.dto.UserFullProfileDTO;
import com.educenter.user_service.dto.UserProfileDTO;
import com.educenter.user_service.entity.User;
import com.educenter.user_service.feign.AuthFeignClient;
import com.educenter.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AuthFeignClient authFeignClient;

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
                    // Llamada al auth-service
                    UserProfileDTO authData = authFeignClient.getUserById(userInfo.getId());

                    // Combinar datos en el DTO final
                    UserFullProfileDTO dto = new UserFullProfileDTO();
                    dto.setId(userInfo.getId());
                    dto.setUsername(authData.getUsername());
                    dto.setEmail(authData.getEmail());
                //    dto.setRole(authData.getRole());
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
