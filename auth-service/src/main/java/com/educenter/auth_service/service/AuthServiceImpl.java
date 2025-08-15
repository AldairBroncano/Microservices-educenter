package com.educenter.auth_service.service;

import com.educenter.auth_service.dto.AuthRegisterDTO;
import com.educenter.auth_service.dto.UserProfileDTO;
import com.educenter.auth_service.entity.Auth;
import com.educenter.auth_service.enums.Role;
import com.educenter.auth_service.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;


    @Override
    public Auth saveUser(Auth auth) {
        return authRepository.save(auth);
    }

    @Override
    public Optional<Auth> getUserByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    @Override
    public Optional<Auth> getUserById(Long id) {
        return authRepository.findById(id);
    }

    @Override
    public void registrar(AuthRegisterDTO dto) {

        Auth auth = new Auth();

        auth.setUsername(dto.getUsername());
        auth.setEmail(dto.getEmail());
        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        auth.setRole(Role.STUDENT);

        Auth savedUser = authRepository.save(auth);

        UserProfileDTO perfil = new UserProfileDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
        restTemplate.postForObject("http://localhost:8082/api/user/crear", perfil, Void.class);


    }



    @Override
    public UserProfileDTO getUserProfileById(Long id) {
        Auth user = authRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new UserProfileDTO(user.getId(), user.getUsername(), user.getEmail());
    }


}
