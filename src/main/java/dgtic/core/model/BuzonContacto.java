package dgtic.core.model;

import dgtic.core.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buzon_contacto")
public class BuzonContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMensajeBuzon")
    private Integer idMensajeBuzon;

    @NotNull
    @Column(name = "tipo", length = 20, nullable = false)
    private String tipo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(name = "mensaje", length = 500, nullable = false)
    private String mensaje;

    @Column(name = "fecha", updatable = false, insertable = false)
    private LocalDateTime fecha;  // MariaDB manejará el valor por defecto

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    // Relación con la tabla Usuarios
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;

    @Column(name = "estado", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'PENDIENTE'")
    private String estado="PENDIENTE";
}