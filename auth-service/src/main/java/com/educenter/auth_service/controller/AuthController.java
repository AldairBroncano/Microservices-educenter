package com.educenter.auth_service.controller;

import com.educenter.auth_service.dto.*;
import com.educenter.auth_service.entity.Auth;
import com.educenter.auth_service.enums.Role;
import com.educenter.auth_service.mapper.AuthMapper;
import com.educenter.auth_service.repository.AuthRepository;
import com.educenter.auth_service.security.JwtProvider;
import com.educenter.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
 @RequiredArgsConstructor
public class AuthController {


//Inyeccion de dependencia por constructor
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;




    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody AuthRegisterDTO dto){

        AuthResponseDTO response = authService.registrar(dto);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/email")
   public ResponseEntity<AuthResponseDTO> getUserByEmail(@RequestParam String email){
        Optional<Auth>optionalUser = authService.getUserByEmail(email);
        return optionalUser
                .map(user -> ResponseEntity.ok(AuthMapper.toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build() );

   }

   @GetMapping("/{id}")
   public ResponseEntity<AuthResponseDTO> getUserById(@PathVariable Long id){
        Optional<Auth> optionalUser = authService.getUserById(id);
        return optionalUser
                .map(user -> ResponseEntity.ok(AuthMapper.toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build() );

   }

   @PostMapping("/login")
   public ResponseEntity<AuthLoginResponseDTO>login(@RequestBody AuthLoginDTO loginDTO){
        AuthLoginResponseDTO response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
   }


    @GetMapping("/profile/{id}")
    public ResponseEntity<UserProfileDTO> getUserProfileId(@PathVariable Long id){
        return ResponseEntity.ok(authService.getUserProfileById(id));
    }


    @GetMapping()
    public List<Auth> getAll(){return authService.getUsersProfiles();}





}






