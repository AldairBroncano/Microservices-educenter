package com.educenter.auth_service.repository;

import com.educenter.auth_service.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth,Long> {

    Optional<Auth> findByUsername(String username);
    Optional<Auth> findByEmail(String email);


}
