package dgtic.core.repository;

import dgtic.core.model.pedido.EstatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstatusPedidoRepository extends JpaRepository<EstatusPedido, Integer> {
    boolean existsByNombreEstatus(String nombreEstatus);
    Optional<EstatusPedido> findByNombreEstatus(String nombreEstatus);
}