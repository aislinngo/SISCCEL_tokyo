package dgtic.core.model.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Perfiles")

public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPerfil;

    @NotNull(message = "Perfil es requerido")
    @NotBlank(message = "Perfil no puede estar vacío")
    @Size(min = 1, max = 50, message = "El nombre de la categoría debe tener entre 1-50 caracteres")
    private String nombrePerfil;

//    @OneToMany(mappedBy = "perfil")
//    @JsonIgnore
//    private List<Usuario> usuarios;
}
