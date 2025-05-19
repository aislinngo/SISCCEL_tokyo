package dgtic.core.controller;

import dgtic.core.model.pedido.EstatusPedido;
import dgtic.core.model.pedido.Pedido;
import dgtic.core.service.BuzonContactoService;
import dgtic.core.service.pedido.EstatusPedidoService;
import dgtic.core.service.pedido.PedidoService;
import dgtic.core.service.producto.CategoriaService;
import dgtic.core.service.producto.ProductoService;
import dgtic.core.service.usuario.PerfilService;
import dgtic.core.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tokyo/administracion/pedido")
public class PedidoController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private EstatusPedidoService estatusPedidoService;

    @Autowired
    private BuzonContactoService buzonContactoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    MessageSource mensaje;

    //ESTATUS PEDIDO

    @GetMapping("ver-guardar-estatus-pedido")
    public String verGuardarEstatusPedido(Model model) {
        model.addAttribute("estatus", new EstatusPedido());
        return "principal/guardar-estatus-pedido";
    }


    @PostMapping("recibir-estatus-pedido")
    public String GuardarEstatusPedido(@Valid EstatusPedido estatusPedido,
                                       BindingResult bindingResult,
                                       Model modelo){
        if(bindingResult.hasErrors()){
            for(ObjectError error: bindingResult.getAllErrors()){
                System.out.println("Error "+ error.getDefaultMessage());
            }
            return "principal/guardar-estatus-pedido";
        }

        // Validar duplicados antes de guardar
        if (estatusPedidoService.existsByNombreEstatus(estatusPedido.getNombreEstatus())) {
            String msg = mensaje.getMessage("Error.estatuspedido.duplicado",
                    null, LocaleContextHolder.getLocale());
            bindingResult.rejectValue("nombreEstatus", "nombreEstatus", msg);
            return "principal/guardar-estatus-pedido";
        }

        try {
            estatusPedidoService.save(estatusPedido);
            modelo.addAttribute("modalMensaje", "Estatus de Pedido agregado con éxito: " + estatusPedido.getNombreEstatus());
        } catch (Exception e) {
            e.printStackTrace();
            modelo.addAttribute("modalMensaje", "Ha ocurrido un error inesperado.");
            return "principal/guardar-estatus-pedido";
        }

        modelo.addAttribute("estatus", new EstatusPedido());
        return "principal/guardar-estatus-pedido";
    }

    @GetMapping("estatus-pedidos")
    public String estatusPedidos(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(required = false) Integer idEstatusPedido,
                                 Model model) {
        PageRequest pageable = PageRequest.of(page, 4);
        Page<Pedido> pedidos;

        if (idEstatusPedido != null) {
            EstatusPedido estatusPedido = estatusPedidoService.findById(idEstatusPedido);
            pedidos = estatusPedido != null
                    ? pedidoService.obtenerPedidosPorEstado(estatusPedido, pageable)
                    : pedidoService.obtenerTodosLosPedidos(pageable);
        } else {
            pedidos = pedidoService.obtenerTodosLosPedidos(pageable);
        }

        List<EstatusPedido> listaEstatus = estatusPedidoService.findAll();

        model.addAttribute("pedidos", pedidos.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pedidos.getTotalPages());
        model.addAttribute("listaEstatus", listaEstatus);
        model.addAttribute("idEstatusPedido", idEstatusPedido);

        return "principal/admin/estatus-pedidos";
    }

    @PostMapping("actualizar-estatus")
    public String actualizarEstatusPedido(@RequestParam("idPedido") Integer idPedido,
                                          @RequestParam("idEstatusPedido") Integer idEstatusPedido,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(required = false) Integer idEstatusPedidoFiltro,
                                          RedirectAttributes redirectAttributes) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(idPedido);
        EstatusPedido nuevoEstatus = estatusPedidoService.findById(idEstatusPedido);

        if (pedido == null || nuevoEstatus == null) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar estatus. Pedido o estatus no encontrado.");
            return "redirect:/tokyo/administracion/pedido/estatus-pedidos?page=" + page + "&idEstatusPedido=" + (idEstatusPedidoFiltro != null ? idEstatusPedidoFiltro : "");
        }

        pedido.setEstatusPedido(nuevoEstatus);
        pedidoService.guardarPedido(pedido);
        redirectAttributes.addFlashAttribute("mensajeExito", "Estatus actualizado correctamente.");

        return "redirect:/tokyo/administracion/pedido/estatus-pedidos?page=" + page + "&idEstatusPedido=" + (idEstatusPedidoFiltro != null ? idEstatusPedidoFiltro : "");
    }


}
