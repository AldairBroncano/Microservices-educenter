package com.educenter.auth_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtProvider {

  private final String jwtSecret = "YbRKhIA0MAAUQtdZSHltsCpK6Hkheav3";
  private final long jwtExpiration = 25 * 60 * 1000;

  public String generateToken(String email){

      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + jwtExpiration);

      return Jwts.builder()
              .setSubject(email)
              .setIssuedAt(now)
              .setExpiration(expiryDate)
              .signWith(SignatureAlgorithm.HS512, jwtSecret)
              .compact();


  }

  public String getEmailFromToken(String token){

      Claims claims = Jwts.parser()
              .setSigningKey(jwtSecret)
              .parseClaimsJws(token)
              .getBody();

      return claims.getSubject();
  }

public boolean validateToken(String token){

}




}
