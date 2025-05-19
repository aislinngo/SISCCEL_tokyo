package dgtic.core.service.pedido;

import dgtic.core.model.pedido.EstatusPedido;
import dgtic.core.repository.EstatusPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstatusPedidoServiceImpl implements EstatusPedidoService {

    @Autowired
    EstatusPedidoRepository estatusPedidoRepository;

    @Override
    @Transactional
    public void save(EstatusPedido estatusPedido) {
        estatusPedidoRepository.save(estatusPedido);
    }

    @Override
    @Transactional
    public List<EstatusPedido> findAll() {
        return estatusPedidoRepository.findAll();
    }

    @Override
    public boolean existsByNombreEstatus(String nombreEstatus) {
        return estatusPedidoRepository.existsByNombreEstatus(nombreEstatus);
    }

    @Override
    public EstatusPedido findById(Integer idEstatusPedido) {
        return estatusPedidoRepository.findById(idEstatusPedido).orElse(null);
    }


}