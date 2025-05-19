package dgtic.core.repository;

import dgtic.core.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByNombre(String nombre);

    Optional <Usuario>  findByEmail(String email);

    boolean existsByEmail(String email);
}