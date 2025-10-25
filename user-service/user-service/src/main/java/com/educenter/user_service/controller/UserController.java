package com.educenter.user_service.controller;

import com.educenter.user_service.dto.UserFullProfileDTO;
import com.educenter.user_service.dto.UserProfileDTO;
import com.educenter.user_service.entity.User;
import com.educenter.user_service.repository.UserRepository;
import com.educenter.user_service.security.TokenContextHolder;
import com.educenter.user_service.service.UserService;
import com.educenter.user_service.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final UserService service;
    private final JwtProvider jwtProvider;

    @PostMapping("/crear")
    public ResponseEntity<User> crearPerfil(@RequestBody User user) {

        // Obtener token desde TokenContextHolder (ya lo guardó tu filter)
        String token = TokenContextHolder.getToken();

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Limpiar token (quitar "Bearer ")
        String cleanToken = token.replace("Bearer ", "");

        // Extraer ID del token usando tu JwtProvider (instancia, no static)
        Long userId = jwtProvider.extractUserId(cleanToken);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Asignar ID automáticamente (ignorar el que mande el cliente)
        user.setId(userId);

        System.out.println("LastName recibido: " + user.getLastName());
        System.out.println("UserId extraído del token: " + userId);

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


    @GetMapping("/full-profiles/{id}")
    public ResponseEntity<UserFullProfileDTO> getFullProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFullProfileById(id));
    }







}
