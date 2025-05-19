package dgtic.core.security;

import dgtic.core.security.jwt.JWTAuthenticationFilter;
import dgtic.core.security.jwt.JWTTokenProvider;
import dgtic.core.security.jwt.JwtAuthenticationSuccessHandler;
import dgtic.core.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTTokenProvider tokenProvider,
                                                   UserDetailsServiceImpl userDetailsService) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Permite recursos estáticos
                        .requestMatchers("/bootstrap/**", "/iconos/**", "/tema/**", "/js/**", "/images/**", "/imagenes/**").permitAll()
                        // Rutas públicas
                        .requestMatchers("/tokyo/", "/tokyo/productos", "/tokyo/contacto", "/tokyo/carrito",
                                "/tokyo/inicio-sesion", "/tokyo/registro").permitAll()


                        //Accesos de administrador
                        .requestMatchers("/tokyo/administracion/**").hasAuthority("Administrador")
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/tokyo/inicio-sesion")
                        .loginProcessingUrl("/tokyo/inicio-sesion")
                        .successHandler(new JwtAuthenticationSuccessHandler(tokenProvider))
                        .failureUrl("/tokyo/inicio-sesion?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/tokyo/")
                        .deleteCookies("JSESSIONID", "token")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true) // Asegurar que la autenticación se elimine completamente
                        .permitAll()
                )
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterAfter(new JWTAuthenticationFilter(tokenProvider, userDetailsService),UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}