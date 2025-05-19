package dgtic.core.service.usuario;

import dgtic.core.model.usuario.Usuario;
import dgtic.core.repository.UsuarioRepository;
import dgtic.core.security.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return usuarioRepository.existsByNombre(nombre);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No hay un usuario autenticado");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return usuarioRepository.findById(userDetails.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado en la base de datos"));
    }

}