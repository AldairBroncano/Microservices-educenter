package com.educenter.auth_service.controller;

import com.educenter.auth_service.dto.UserLoginDTO;
import com.educenter.auth_service.dto.UserResponseDTO;
import com.educenter.auth_service.dto.UserRegisterDTO;
import com.educenter.auth_service.entity.User;
import com.educenter.auth_service.mapper.UserMapper;
import com.educenter.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegisterDTO userDTO){
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        User savedUser = userService.saveUser(user);

        return ResponseEntity.ok(UserMapper.toDTO(savedUser));
    }

    @GetMapping("/email")
   public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email){
        Optional<User>optionalUser = userService.getUserByEmail(email);
        return optionalUser
                .map(user -> ResponseEntity.ok(UserMapper.toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build() );

   }

   @GetMapping("/{id}")
   public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        Optional<User> optionalUser = userService.getUserById(id);
        return optionalUser
                .map(user -> ResponseEntity.ok(UserMapper.toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build() );

   }


   @PostMapping("/login")
public ResponseEntity<?>Login(@RequestBody UserLoginDTO loginDTO){

      Optional<User> optionalUser = userService.getUserByEmail(loginDTO.getEmail());

      if (optionalUser.isEmpty()){
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
      }

      User user = optionalUser.get();


      if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
      }

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Login exitoso");
    response.put("email", user.getEmail());
    response.put("username", user.getUsername());
    response.put("role", user.getRole());

    return ResponseEntity.ok(response);



}





}
