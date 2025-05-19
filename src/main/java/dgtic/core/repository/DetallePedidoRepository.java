package dgtic.core.repository;

import dgtic.core.model.pedido.DetallePedido;
import dgtic.core.model.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    List<DetallePedido> findByPedido(Pedido pedido);

}
