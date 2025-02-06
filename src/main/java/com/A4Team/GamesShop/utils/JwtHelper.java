package com.A4Team.GamesShop.utils;

import com.A4Team.GamesShop.dto.UserDTO;
import com.A4Team.GamesShop.exception.JwtExceptionCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {

    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${jwt.tokenExpirationMs}")
    private long tokenExpirationMs;

    public String getDataToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
        String data = "";
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            data = claims.get("role", String.class);
        }catch (ExpiredJwtException e) {
            throw new JwtExceptionCustom("Token expired");
        } catch (MalformedJwtException e) {
            throw new JwtExceptionCustom("Invalid token format");
        } catch (UnsupportedJwtException e) {
            throw new JwtExceptionCustom("Unsupported token");
        } catch (SecurityException e) {
            throw new JwtExceptionCustom("Invalid signature");
        } catch (JwtException e) {
            throw new JwtExceptionCustom("JWT processing error");
        }
        return data;
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
    }

    public String generateToken(UserDTO userDTO) {
        return Jwts.builder()
                .subject(String.valueOf(userDTO.getId()))
                .claim("email", userDTO.getEmail())
                .claim("name", userDTO.getName())
                .claim("avatar", userDTO.getAvatar())
                .claim("role", userDTO.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

}
