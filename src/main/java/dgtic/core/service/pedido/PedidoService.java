package dgtic.core.service.pedido;

import dgtic.core.model.pedido.DetallePedido;
import dgtic.core.model.pedido.EstatusPedido;
import dgtic.core.model.pedido.Pedido;
import dgtic.core.model.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PedidoService {
    Pedido confirmarPedido(Usuario usuario);
    List<Pedido> obtenerPedidosPorUsuario(Usuario usuario);

    @Transactional
    void eliminarCarritoPorUsuario(Usuario usuario);

    List<DetallePedido> obtenerDetallesPedido(Pedido pedido);

    Pedido obtenerPedidoPorId(Integer idPedido);

    Page<Pedido> obtenerPedidosPorUsuario(Usuario usuario, PageRequest pageable);

    Page<Pedido> obtenerPedidosPorUsuarioYEstado(Usuario usuario, EstatusPedido estatusPedido, PageRequest pageable);

    Page<Pedido> obtenerTodosLosPedidos(Pageable pageable);

    Page<Pedido> obtenerPedidosPorEstado(EstatusPedido estatusPedido, Pageable pageable);

    void guardarPedido(Pedido pedido);

}
