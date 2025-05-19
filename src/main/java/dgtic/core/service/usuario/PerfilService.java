package dgtic.core.service.usuario;

import dgtic.core.model.usuario.Perfil;

import java.util.List;

public interface PerfilService {

    void save(Perfil perfil);

    List<Perfil> findAll();

    boolean existsByNombrePerfil(String nombrePerfil);
}

