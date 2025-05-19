package dgtic.core.model.pedido;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EstatusPedido")

public class EstatusPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstatusPedido;

    @NotNull(message = "Estatus de Pedido es requerido")
    @NotBlank(message = "Estatus de Pedido no puede estar vacío")
    @Size(min = 1, max = 50, message = "El nombre del Estatus del Pedido debe tener entre 1-50 caracteres")
    private String nombreEstatus;
}
