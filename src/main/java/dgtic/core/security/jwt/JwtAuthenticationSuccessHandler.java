package dgtic.core.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTTokenProvider jwtTokenProvider;

    public JwtAuthenticationSuccessHandler(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. Genera JWT token
        String token = jwtTokenProvider.generateJwtToken(authentication);

        // 2. Crea una HTTP-only cookie segura
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setPath("/");
        cookie.setMaxAge(jwtTokenProvider.getExpiryDuration());
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        // 3. Redireccion segun perfil del usuario
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Administrador"));

        if (isAdmin) {
            response.sendRedirect(request.getContextPath() + "/tokyo/administracion/pedido/estatus-pedidos");
        } else {
            response.sendRedirect(request.getContextPath() + "/tokyo/");
        }
    }
}
