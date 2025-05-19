package dgtic.core.controller;

import dgtic.core.model.BuzonContacto;
import dgtic.core.model.usuario.Perfil;
import dgtic.core.model.usuario.Usuario;
import dgtic.core.service.BuzonContactoService;
import dgtic.core.service.usuario.PerfilService;
import dgtic.core.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tokyo/administracion/usuario")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private BuzonContactoService buzonContactoService;

    @Autowired
    private MessageSource mensaje;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //PERFIL
//    Ver formulario para guardar perfil
    @GetMapping("guardar-perfil")
    public String verGuardarPerfil(Model model) {
        model.addAttribute("perfil", new Perfil());
        return "principal/guardar-perfil";
    }


    //    Guardar perfil
    @PostMapping("guardar-perfil")
    public String GuardarPerfil(@Valid Perfil perfil,
                                BindingResult bindingResult,
                                Model modelo){
        if(bindingResult.hasErrors()){
            for(ObjectError error: bindingResult.getAllErrors()){
                System.out.println("Error "+ error.getDefaultMessage());
            }
            return "principal/guardar-perfil";
        }

        // Validar duplicados antes de guardar
        if (perfilService.existsByNombrePerfil(perfil.getNombrePerfil())) {
            String msg = mensaje.getMessage("Error.perfil.duplicado",
                    null, LocaleContextHolder.getLocale());
            bindingResult.rejectValue("nombrePerfil", "nombrePerfil", msg);
            return "principal/guardar-perfil";
        }

        try {
            perfilService.save(perfil);
            modelo.addAttribute("modalMensaje", "Perfil guardado con éxito: " + perfil.getNombrePerfil());
        } catch (Exception e) {
            e.printStackTrace();
            modelo.addAttribute("modalMensaje", "Ha ocurrido un error inesperado.");
            return "principal/guardar-perfil";
        }

        modelo.addAttribute("perfil", new Perfil());
        return "principal/guardar-perfil";
    }

    //--------------------------REGISTRO DE USUARIO ADMINISTRADOR --------------------------------------------------------------

    // 2) Registro de Administrador (nuevo)

    @GetMapping("registro-admin")
//    @PreAuthorize("hasAuthority('Administrador')")           // opcional: solo Admin puede abrir este form
    public String verFormularioAdmin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "principal/admin/registro-admin";
    }

    @PostMapping("registro-admin")
//    @PreAuthorize("hasAuthority('Administrador')")           // opcional: solo Admin puede crear otro admin
    public String guardarAdmin(@Valid Usuario usuario,
                               BindingResult br,
                               Model model,
                               RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "principal/admin/registro-admin";
        }
        // Verificar duplicado, igual que antes…
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            br.rejectValue("email", "email.duplicated", "El email ya está registrado.");
            return "principal/admin/registro-admin";
        }
        // Asignar perfil Administrador
        Perfil perfilAdmin = perfilService.findAll().stream()
                .filter(p -> "Administrador".equalsIgnoreCase(p.getNombrePerfil()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Perfil 'Administrador' no encontrado."));
        usuario.setPerfil(perfilAdmin);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioService.save(usuario);

        ra.addFlashAttribute("modalMensaje", "Administrador registrado exitosamente.");
        return "redirect:/tokyo/";
    }

    //Buzón de entrada de Quejas y Sugerencias (Admin)

    //    Ver página de buzón de entrada
    @GetMapping("buzon-entrada")
    public String verBuzonEntrada(
            @RequestParam(name = "tipo", required = false) String tipo,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10); // 10 registros por página
        Page<BuzonContacto> buzonesPage;

        // Si se envía un parámetro tipo y este no está vacío, filtra por ese valor.
        if (tipo != null && !tipo.trim().isEmpty()) {
            buzonesPage = buzonContactoService.findByTipoOrderByFechaDesc(tipo, pageable);
        } else {
            buzonesPage = buzonContactoService.findAllByOrderByFechaDesc(pageable);
        }

        model.addAttribute("page", buzonesPage);
        // Opción que está seleccionada en el filtrado:
        model.addAttribute("tipoSeleccionado", tipo);
        return "principal/admin/buzon-entrada";
    }
}
