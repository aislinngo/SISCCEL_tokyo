package dgtic.core.service.pedido;

import dgtic.core.model.inventario.Inventario;
import dgtic.core.model.pedido.DetallePedido;
import dgtic.core.model.pedido.EstatusPedido;
import dgtic.core.model.pedido.Pedido;
import dgtic.core.model.usuario.Usuario;
import dgtic.core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private EstatusPedidoRepository estatusPedidoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    @Transactional
    public Pedido confirmarPedido(Usuario usuario) {
        List<DetallePedido> detallesPedido = carritoRepository.findByUsuario(usuario).stream().map(item -> {
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(item.getProducto());
            detalle.setCantidadProductos(item.getCantidadProductos());
            detalle.setSubtotal(item.getSubtotal());
            return detalle;
        }).toList();

        if (detallesPedido.isEmpty()) {
            throw new RuntimeException("No puedes confirmar un pedido sin productos.");
        }

        EstatusPedido estatusPedido = estatusPedidoRepository.findByNombreEstatus("Pendiente")
                .orElseThrow(() -> new RuntimeException("Estatus 'Pendiente' no encontrado"));

        BigDecimal total = detallesPedido.stream()
                .map(detalle -> BigDecimal.valueOf(detalle.getSubtotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEstatusPedido(estatusPedido);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setTotalPedido(total);
        pedidoRepository.save(pedido);

        detallesPedido.forEach(detalle -> {
            detalle.setPedido(pedido);
            detallePedidoRepository.save(detalle);

            // Descontar la cantidad del inventario
            Inventario inventario = inventarioRepository.findByProducto(detalle.getProducto())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el producto: " + detalle.getProducto().getNombreProducto()));

            int nuevaCantidad = inventario.getNumeroExistencias() - detalle.getCantidadProductos();
            if (nuevaCantidad < 0) {
                throw new RuntimeException("No hay suficiente stock para el producto: " + detalle.getProducto().getNombreProducto());
            }

            inventario.setNumeroExistencias(nuevaCantidad);
            inventario.setUltimoInventario(LocalDateTime.now());
            inventarioRepository.save(inventario);
        });

        carritoRepository.deleteByUsuario(usuario);

        return pedido;
    }

    @Override
    public List<Pedido> obtenerPedidosPorUsuario(Usuario usuario) {
        return pedidoRepository.findAll();
    }

    @Transactional
    @Override
    public void eliminarCarritoPorUsuario(Usuario usuario) {
        carritoRepository.deleteByUsuario(usuario);
    }

    @Override
    public List<DetallePedido> obtenerDetallesPedido(Pedido pedido) {
        return detallePedidoRepository.findByPedido(pedido);
    }

    @Override
    public Pedido obtenerPedidoPorId(Integer idPedido) {
        return pedidoRepository.findById(idPedido).orElse(null);
    }

    @Override
    public Page<Pedido> obtenerPedidosPorUsuario(Usuario usuario, PageRequest pageable) {
        return pedidoRepository.findByUsuario(usuario, pageable);
    }

    @Override
    public Page<Pedido> obtenerPedidosPorUsuarioYEstado(Usuario usuario, EstatusPedido estatusPedido, PageRequest pageable) {
        return pedidoRepository.findByUsuarioAndEstatusPedido(usuario, estatusPedido, pageable);
    }

    @Override
    public Page<Pedido> obtenerTodosLosPedidos(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    @Override
    public Page<Pedido> obtenerPedidosPorEstado(EstatusPedido estatusPedido, Pageable pageable) {
        return pedidoRepository.findByEstatusPedido(estatusPedido, pageable);
    }

    @Override
    @Transactional
    public void guardarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }
}
