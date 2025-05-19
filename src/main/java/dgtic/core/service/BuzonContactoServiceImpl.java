package dgtic.core.service;

import dgtic.core.model.BuzonContacto;
import dgtic.core.repository.BuzonContactoRepository;
import dgtic.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuzonContactoServiceImpl implements BuzonContactoService {

    @Autowired
    BuzonContactoRepository buzonContactoRepository;


    @Override
    @Transactional
    public void save(BuzonContacto buzonContacto) {
        buzonContactoRepository.save(buzonContacto);
    }

    @Override
    @Transactional
    public List<BuzonContacto> findAll() {
        return buzonContactoRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Page<BuzonContacto> findAllByOrderByFechaDesc(Pageable pageable) {
        return buzonContactoRepository.findAllByOrderByFechaDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuzonContacto> findByTipoOrderByFechaDesc(String tipo, Pageable pageable) {
        return buzonContactoRepository.findByTipoOrderByFechaDesc(tipo, pageable);
    }



}