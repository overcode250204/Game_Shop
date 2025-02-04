package com.A4Team.GamesShop.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {
    @Value("${jwt.key}")
    private String jwtKey;

}
