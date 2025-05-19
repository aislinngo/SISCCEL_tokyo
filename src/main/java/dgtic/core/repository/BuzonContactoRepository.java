package dgtic.core.repository;

import dgtic.core.model.BuzonContacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuzonContactoRepository extends JpaRepository<BuzonContacto, Integer> {
    Page<BuzonContacto> findAllByOrderByFechaDesc(Pageable pageable);
    Page<BuzonContacto> findByTipoOrderByFechaDesc(String tipo, Pageable pageable);


}