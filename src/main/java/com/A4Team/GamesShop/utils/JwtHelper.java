package com.A4Team.GamesShop.utils;

import com.A4Team.GamesShop.enums.UserRoleEnum;
import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.exception.JwtExceptionCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {

    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${jwt.tokenExpirationMs}")
    private long tokenExpirationMs;

    public UserAuthResponse getDataToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
        UserAuthResponse data = new UserAuthResponse();
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            data.setId(Integer.parseInt(claims.getSubject()));
            data.setEmail(claims.get("email", String.class));
            data.setName(claims.get("name", String.class));
            data.setAvatar(claims.get("avatar", String.class));
            data.setRole(UserRoleEnum.valueOf(claims.get("role", String.class)));
        }catch (ExpiredJwtException e) {
            throw new JwtExceptionCustom("Token expired", HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            throw new JwtExceptionCustom("Invalid token format", HttpStatus.BAD_REQUEST);
        } catch (UnsupportedJwtException e) {
            throw new JwtExceptionCustom("Unsupported token", HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            throw new JwtExceptionCustom("Invalid signature", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new JwtExceptionCustom("JWT processing error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return data;
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
    }

    public String generateToken(UserAuthResponse userDTO) {
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
