package com.educenter.auth_service.service;

import com.educenter.auth_service.dto.*;
import com.educenter.auth_service.entity.Auth;
import com.educenter.auth_service.enums.Role;
import com.educenter.auth_service.mapper.AuthMapper;
import com.educenter.auth_service.repository.AuthRepository;
import com.educenter.auth_service.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;



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
    public AuthLoginResponseDTO login(AuthLoginDTO dto){
        try{

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );

            Auth auth = authRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String token = jwtProvider.generateToken(
                    auth.getEmail(),
                    auth.getId(),
                    auth.getRole(),
                    auth.getUser()
            );

            return new AuthLoginResponseDTO(
                    token,
                    auth.getId(),
                    auth.getEmail(),
                    auth.getRole()
            );
        }catch (Exception e){
            throw new RuntimeException("Credenciales incorrectas");
        }


    }



    @Override
    public AuthResponseDTO registrar(AuthRegisterDTO dto) {

        //USAR MAPPER
        Auth auth = AuthMapper.toEntity(dto);

        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        auth.setRole(Role.STUDENT);

        Auth savedUser = authRepository.save(auth);

        return AuthMapper.toDTO(savedUser);
    }



    @Override
    public UserProfileDTO getUserProfileById(Long id) {
        Auth user = authRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new UserProfileDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    @Override
    public List<Auth> getUsersProfiles() {
        return authRepository.findAll();
    }








}
