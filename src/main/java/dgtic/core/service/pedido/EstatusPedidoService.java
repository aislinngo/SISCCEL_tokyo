package dgtic.core.service.pedido;

import dgtic.core.model.pedido.EstatusPedido;

import java.util.List;

public interface EstatusPedidoService {

    void save(EstatusPedido estatusPedido);

    List<EstatusPedido> findAll();

    boolean existsByNombreEstatus(String nombreEstatus);

    EstatusPedido findById(Integer idEstatusPedido);

}

