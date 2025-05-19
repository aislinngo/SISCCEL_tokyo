package dgtic.core.service;

import dgtic.core.model.BuzonContacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BuzonContactoService {

    void save(BuzonContacto buzonContacto);

    List<BuzonContacto> findAll();

    Page<BuzonContacto> findAllByOrderByFechaDesc(Pageable pageable);
    Page<BuzonContacto> findByTipoOrderByFechaDesc(String tipo, Pageable pageable);


}

