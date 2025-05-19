package dgtic.core.repository;

import dgtic.core.model.pedido.EstatusPedido;
import dgtic.core.model.pedido.Pedido;
import dgtic.core.model.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Page<Pedido> findByUsuario(Usuario usuario, Pageable pageable);
    Page<Pedido> findByUsuarioAndEstatusPedido(Usuario usuario, EstatusPedido estatusPedido, Pageable pageable);
    Page<Pedido> findByEstatusPedido(EstatusPedido estatusPedido, Pageable pageable);
}
