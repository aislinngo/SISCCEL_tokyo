package dgtic.core.repository;

import dgtic.core.model.usuario.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    boolean existsByNombrePerfil(String nombrePerfil);


}