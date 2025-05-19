package dgtic.core.service.usuario;

import dgtic.core.model.usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    void save(Usuario usuario);

    List<Usuario> findAll();

    boolean existsByNombre(String nombre);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    Usuario obtenerUsuarioAutenticado();
}

