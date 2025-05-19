package dgtic.core.controller;

import dgtic.core.model.BuzonContacto;
import dgtic.core.model.pedido.EstatusPedido;
import dgtic.core.model.pedido.Pedido;
import dgtic.core.model.usuario.Perfil;
import dgtic.core.model.usuario.Usuario;
import dgtic.core.service.BuzonContactoService;
import dgtic.core.service.pedido.EstatusPedidoService;
import dgtic.core.service.pedido.PedidoService;
import dgtic.core.service.usuario.PerfilService;
import dgtic.core.service.usuario.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "tokyo")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EstatusPedidoService estatusPedidoService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private BuzonContactoService buzonContactoService;

    @Autowired
    private MessageSource mensaje;

    @Autowired
    private PasswordEncoder passwordEncoder;



    //--------------------------REGISTRO DE USUARIO CLIENTE --------------------------------------------------------------

    // Ver formulario de registro de usuario
    @GetMapping("registro")
    public String verGuardarUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "principal/usuario/registro";
    }

    // Guardar el registro de usuario
    @PostMapping("registro")
    public String guardarUsuario(@Valid Usuario usuario,
                                 BindingResult bindingResult,
                                 Model modelo,
                                 RedirectAttributes redirectAttributes) {
        System.out.println(usuario.getNombre());
        // Validar campos requeridos
        if (bindingResult.hasErrors()) {
            modelo.addAttribute("usuario", usuario);
            return "principal/usuario/registro";
        }

        // Validar duplicidad del email
        if (usuarioService.existsByEmail( usuario.getEmail())){
            bindingResult.rejectValue("email", "email.duplicated", "El email ya está registrado.");
            modelo.addAttribute("usuario", usuario);
            return "principal/usuario/registro";
        }

        try {
            // Asignar perfil "Cliente" desde la base de datos
            Perfil perfilCliente = perfilService.findAll().stream()
                    .filter(perfil -> "Cliente".equalsIgnoreCase(perfil.getNombrePerfil()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Perfil 'Cliente' no encontrado."));
            usuario.setPerfil(perfilCliente);

            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

            // Guardar el usuario en la base de datos
            usuarioService.save(usuario);

            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario registrado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error inesperado al registrar al usuario.");
            return "redirect:/tokyo/registro";
        }


        return "redirect:/tokyo/inicio-sesion";
    }

    //--------------------------INICIO DE SESIÓN--------------------------------------------------------------

    // Pantalla para iniciar sesión
    @GetMapping("inicio-sesion")
    public String inicioSesion(Model model) {
        model.addAttribute("mensaje", "");
        return "principal/inicio-sesion";
    }

    //--------------------------CONTACTO--------------------------------------------------------------

    @GetMapping("contacto")
    public String mostrarFormulario(Model model) {
        model.addAttribute("buzon", new BuzonContacto());
        return "principal/contacto";
    }

//    Enviar formulario de queja o sugerencia
    @PostMapping("contacto")
    public String enviarFormulario(@ModelAttribute @Valid BuzonContacto buzon,
                                   BindingResult bindingResult,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "principal/contacto";
        }

        try {
            if (session.getAttribute("usuarioActivo") != null) {
                Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
                buzon.setUsuario(usuarioActivo);
            }

            buzonContactoService.save(buzon);
            redirectAttributes.addFlashAttribute("modalMensaje", "Tu mensaje ha sido enviado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("modalMensaje", "Ocurrió un error al enviar tu mensaje.");
            return "redirect:/tokyo/contacto";
        }

        return "redirect:/tokyo/contacto";
    }

    @GetMapping("pedidos")
    public String pedidos(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(required = false) Integer idEstatusPedido,
                          Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/tokyo/inicio-sesion";
        }

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        PageRequest pageable = PageRequest.of(page, 4);

        Page<Pedido> pedidos;
        if (idEstatusPedido != null) {
            EstatusPedido estatusPedido = estatusPedidoService.findById(idEstatusPedido);
            pedidos = estatusPedido != null
                    ? pedidoService.obtenerPedidosPorUsuarioYEstado(usuario, estatusPedido, pageable)
                    : pedidoService.obtenerPedidosPorUsuario(usuario, pageable);
        } else {
            pedidos = pedidoService.obtenerPedidosPorUsuario(usuario, pageable);
        }

        // Obtener lista de estatus desde el servicio
        List<EstatusPedido> listaEstatus = estatusPedidoService.findAll();

        model.addAttribute("pedidos", pedidos.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pedidos.getTotalPages());
        model.addAttribute("listaEstatus", listaEstatus);
        model.addAttribute("idEstatusPedido", idEstatusPedido);

        return "principal/pedidos-cliente";
    }

}
