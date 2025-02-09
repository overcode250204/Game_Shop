package com.A4Team.GamesShop.filter;

import com.A4Team.GamesShop.model.response.UserAuthResponse;
import com.A4Team.GamesShop.exception.JwtExceptionCustom;
import com.A4Team.GamesShop.utils.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class CustomSecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;
    @Value("${url.authUrl}")
    private String authUrl;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if(authUrl.equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars")) {
            filterChain.doFilter(request,response);
            return;
        }

        if (path.startsWith("/auth/google/google-url") || path.startsWith("/auth/google/google-login")) {
            filterChain.doFilter(request,response);
            return;
        }


        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try{
                UserAuthResponse user = jwtHelper.getDataToken(token);
                String roleName = Optional.ofNullable(user.getRole())
                        .map(Enum::name)
                        .orElseThrow(() -> new JwtExceptionCustom("User role is missing", HttpStatus.UNAUTHORIZED));
                List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(roleName));
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, authorityList);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (JwtExceptionCustom e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }
        filterChain.doFilter(request, response);
    }

}
