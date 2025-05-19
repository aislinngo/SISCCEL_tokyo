package dgtic.core.security.jwt;

import dgtic.core.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Extraer el token JWT de las cookies
        String jwt = extractTokenFromCookies(request);

        if (jwt != null && jwtTokenProvider.validateJwtToken(jwt)) {
            // 2. Obtener claims del token
            Claims claims = jwtTokenProvider.getClaims(jwt);
            String username = claims.getSubject();

            // 3. Cargar UserDetails y verificar perfil
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails.getAuthorities().isEmpty()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuario sin perfil asignado");
                return;
            }

            // 4. Crear autenticación y establecer en el contexto de seguridad
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 5. Limpiar contexto si no hay token válido
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}