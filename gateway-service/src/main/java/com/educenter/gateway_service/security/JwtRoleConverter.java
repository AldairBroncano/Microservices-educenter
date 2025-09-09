package com.educenter.gateway_service.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;

public class JwtRoleConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        String role = jwt.getClaimAsString("role");
        Collection<GrantedAuthority> authorities = List.of();

        if(role != null){
            authorities = List.of(new SimpleGrantedAuthority("ROLE_" +  role));
        }

        System.out.println("➡️ JWT role claim: " + role);
        System.out.println("➡️ Authorities asignadas: " + authorities);


        return new JwtAuthenticationToken(jwt, authorities);
    }


}
