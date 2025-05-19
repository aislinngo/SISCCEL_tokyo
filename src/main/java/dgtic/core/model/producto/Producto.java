package dgtic.core.model.producto;

import dgtic.core.model.inventario.Inventario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @NotBlank(message = "Nombre del producto no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del producto no puede exceder los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombreProducto;

    @NotBlank(message = "Descripción del producto es requerida")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "Precio es requerido")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria categoria;

    @Column(nullable = false, length = 50)
    private String imagen;

    @Column(nullable = false, length = 50, unique = true)
    private String sku;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToOne(mappedBy = "producto")
    private Inventario inventario;
}
