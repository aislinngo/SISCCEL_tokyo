package dgtic.core.service.usuario;

import dgtic.core.model.usuario.Perfil;
import dgtic.core.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    PerfilRepository perfilRepository;

    @Override
    @Transactional
    public void save(Perfil perfil) {
        perfilRepository.save(perfil);
    }

    @Override
    @Transactional
    public List<Perfil> findAll() {
        return perfilRepository.findAll();
    }

    @Override
    public boolean existsByNombrePerfil(String nombrePerfil) {
        return perfilRepository.existsByNombrePerfil(nombrePerfil);
    }

}