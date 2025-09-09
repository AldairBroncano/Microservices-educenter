package com.educenter.auth_service.controller;

import com.educenter.auth_service.dto.AuthLoginDTO;
import com.educenter.auth_service.dto.AuthResponseDTO;
import com.educenter.auth_service.dto.AuthRegisterDTO;
import com.educenter.auth_service.dto.UserProfileDTO;
import com.educenter.auth_service.entity.Auth;
import com.educenter.auth_service.enums.Role;
import com.educenter.auth_service.mapper.AuthMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody AuthRegisterDTO userDTO){
        Auth auth = AuthMapper.toEntity(userDTO);
        auth.setPassword(passwordEncoder.encode(auth.getPassword()));
        auth.setRole(Role.ADMIN);
        Auth savedAuth = authService.saveUser(auth);

        return ResponseEntity.ok(AuthMapper.toDTO(savedAuth));
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
    public ResponseEntity<?> login(@RequestBody AuthLoginDTO loginDTO) {
        try {
            // Autenticación con AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );

            // Obtener el usuario autenticado (UserDetails)
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            // Buscar datos extra si necesitas más que el email (como username o role)
            Optional<Auth> optionalUser = authService.getUserByEmail(userDetails.getUsername());

            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Datos del usuario no encontrados");
            }

            Auth auth = optionalUser.get();


// 🔐 Generar el token JWT
            String token = jwtProvider.generateToken(userDetails.getUsername() , auth.getId(), auth.getRole() ); // username = email
            System.out.println("TOKEN GENERADO: " + token);

            // 📦 Armar la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("token", token);
            response.put("userId", auth.getId());
            response.put("email", auth.getEmail());
            response.put("username", auth.getUsername());
            response.put("role", auth.getRole());

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }


    @GetMapping("/profile/{id}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long id){
        return ResponseEntity.ok(authService.getUserProfileById(id));
    }






}






