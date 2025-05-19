package dgtic.core.model.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @NotNull(message = "El nombre es requerido")
    @NotBlank(message = "El campo Nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2-50 caracteres")
    private String nombre;

    @NotNull(message = "Apellido paterno es requerido")
    @NotBlank(message = "Apellido Paterno puede estar vacío")
    @Size(min = 2, max = 50, message = "Apellido Materno debe tener entre 2-50 caracteres")
    private String apellidoPaterno;

    @NotNull(message = "Apellido Materno es requerido")
    @NotBlank(message = "Apellido Materno no puede estar vacío")
    @Size(min = 2, max = 50, message = "Apellido Materno debe tener entre 2-50 caracteres")
    private String apellidoMaterno;

    @NotNull(message = "Email es requerido")
    @NotBlank(message = "Email no puede ir vacío")
    @Size(max = 100, message = "Email debe tener menos de 100 caracteres")
    @Email(message = "Email incorrecto, ingrese un formato valido")
    @Column(unique = true) // Para garantizar que no hay emails duplicados
    private String email;


    @NotNull(message = "La contraseña es requerido")
    @NotBlank(message = "Contraseña no puede estar vacío")
//    @Size(min = 8, max = 50, message = "La Contraseña debe tener entre 8-50 caracteres")
    private String contrasena;

    @NotNull(message = "Direccion es requerido")
    @NotBlank(message = "Direccion es requerido")
    @Size(max = 300, message = "Direccion debe tener menos de 300 caracteres")
    private String direccion;

    @NotNull(message = "Teléfono es requerido")
    @NotBlank(message = "Teléfono no puede estar vacío")
    @Size(max = 10, message = "Teléfono debe tener 10 dígitos")
    @Pattern(regexp = "\\d{10}", message = "El Teléfono debe contener exactamente 10 dígitos numéricos")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "idPerfil")
    @ToString.Exclude
    private Perfil perfil;


}
