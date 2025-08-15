package com.educenter.user_service.controller;

import com.educenter.user_service.dto.UserFullProfileDTO;
import com.educenter.user_service.dto.UserProfileDTO;
import com.educenter.user_service.entity.User;
import com.educenter.user_service.repository.UserRepository;
import com.educenter.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final UserService service;

@PostMapping("/crear")
public ResponseEntity<User> crearPerfil(@RequestBody User user){
    System.out.println("LastName recibido: " + user.getLastName());
    User saved = service.guardar(user);
    return ResponseEntity.ok(saved);
}

    @GetMapping("/{id}")
    public ResponseEntity<User> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

  /*  @GetMapping("/email/{email}")
    public ResponseEntity<User> obtenerPorEmail(@PathVariable String email) {
        return service.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


   */

@GetMapping("/auth-profile/{id}")
public ResponseEntity<UserProfileDTO> obtenerPerfilDesdeAuth(@PathVariable Long id){
    UserProfileDTO dto = service.obternerPerfilDesdeAuth(id);
    return ResponseEntity.ok(dto);
}

    @GetMapping("/full-profiles")
    public ResponseEntity<List<UserFullProfileDTO>> obtenerPerfilesCompletos() {
        List<UserFullProfileDTO> perfiles = service.getAllFullProfiles();
        return ResponseEntity.ok(perfiles);
    }



}
