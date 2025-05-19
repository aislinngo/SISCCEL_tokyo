package dgtic.core.controller;


import dgtic.core.model.pedido.Carrito;
import dgtic.core.model.pedido.DetallePedido;
import dgtic.core.model.pedido.Pedido;
import dgtic.core.model.usuario.Usuario;
import dgtic.core.service.pedido.CarritoService;
import dgtic.core.service.pedido.PedidoService;
import dgtic.core.service.usuario.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "tokyo")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("agregar-carrito")
    public String agregarAlCarrito(@RequestParam("idProducto") Integer idProducto,
                                   @RequestParam("cantidad") Integer cantidad,
                                   RedirectAttributes redirectAttributes,
                                   HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/tokyo/inicio-sesion";
        }

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        carritoService.agregarAlCarrito(usuario, idProducto, cantidad);
        redirectAttributes.addFlashAttribute("modalMensaje", "Producto añadido al carrito.");

        // Capturar URL previa y redirigir de vuelta
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/tokyo/");
    }

    @GetMapping("carrito")
    public String carrito(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/tokyo/inicio-sesion";
        }

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        List<Carrito> carrito = carritoService.obtenerCarritoPorUsuario(usuario);
        model.addAttribute("carrito", carrito);
        return "principal/carrito";
    }

    @PostMapping("eliminar-carrito")
    public String eliminarDelCarrito(@RequestParam("idProducto") Integer idProducto,
                                     RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/tokyo/inicio-sesion";
        }

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        carritoService.eliminarDelCarrito(usuario, idProducto);
        redirectAttributes.addFlashAttribute("mensajeExito", "Producto eliminado del carrito.");
        return "redirect:/tokyo/carrito";
    }

    @GetMapping("pedido")
    public String pedido(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/tokyo/inicio-sesion";
        }

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        List<Carrito> carrito = carritoService.obtenerCarritoPorUsuario(usuario);

        model.addAttribute("carrito", carrito);
        model.addAttribute("usuario", usuario); // Para mostrar la dirección de envío
        return "principal/pedido";
    }

    @PostMapping("confirmar-pedido")
    public String confirmarPedido(RedirectAttributes redirectAttributes, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/tokyo/inicio-sesion";
        }

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();

        try {
            Pedido pedido = pedidoService.confirmarPedido(usuario);
            List<DetallePedido> detallesPedido = pedidoService.obtenerDetallesPedido(pedido); // Recupera los detalles correctamente

            model.addAttribute("pedido", pedido);
            model.addAttribute("detallePedidos", detallesPedido);
            redirectAttributes.addFlashAttribute("mensajeExito", "Pedido confirmado exitosamente. Será atendido en los próximos días.");
            return "principal/pedido-exito";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tokyo/pedido";
        }
    }

    @GetMapping("/generar-comprobante")
    public String generarComprobante(@RequestParam("idPedido") Integer idPedido,
                                     @RequestParam(required = false) String format,
                                     Model model) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(idPedido);
        if (pedido == null) {
            model.addAttribute("mensajeError", "El pedido no existe.");
            return "redirect:/tokyo/";
        }

        model.addAttribute("pedido", pedido);
        model.addAttribute("detallePedidos", pedido.getDetallePedidos());

        if ("pdf".equalsIgnoreCase(format)) {
            return "comprobante-pdf";
        }

        return "pedido-exito";
    }

}
