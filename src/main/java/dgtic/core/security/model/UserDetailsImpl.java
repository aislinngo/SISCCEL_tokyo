package dgtic.core.security.model;

import dgtic.core.model.usuario.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo");
        }
        if (usuario.getPerfil() == null) {
            throw new IllegalStateException("El usuario debe tener un perfil asignado");
        }
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
                new SimpleGrantedAuthority(usuario.getPerfil().getNombrePerfil())
        );
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    // Métodos adicionales para acceder a información del usuario
    public String getNombre() {
        return usuario.getNombre();
    }

    public String getNombreCompleto() {
        return usuario.getNombre() + " " + usuario.getApellidoPaterno();
    }

    public Integer getIdUsuario() {
        return usuario.getIdUsuario();
    }

    // Implementación de los métodos de estado de la cuenta
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Metodo para acceder al objeto Usuario completo
    public Usuario getUsuario() {
        return usuario;
    }
}