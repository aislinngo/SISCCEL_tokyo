package dgtic.core.util.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import dgtic.core.model.pedido.Pedido;
import dgtic.core.model.pedido.DetallePedido;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.Color;
import java.util.List;
import java.util.Map;

@Component("comprobante-pdf")
public class ResumenCompraPDF extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "inline; filename=\"comprobante-compra.pdf\"");

        Pedido pedido = (Pedido) model.get("pedido");
        List<DetallePedido> detallesPedido = pedido.getDetallePedidos();

        // Agregar logo
        try {
            String logoPath = "C:/imagenes/flor-cerezo.png";
            Image logo = Image.getInstance(logoPath);
            logo.scaleAbsolute(60, 60);
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            // Si hay error, se continúa sin logo.
        }

        // Título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
        Paragraph title = new Paragraph("Comprobante de Compra", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Información del pedido
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
        Paragraph infoPedido = new Paragraph(
                "Pedido No: " + pedido.getIdPedido() +
                        "\nFecha: " + pedido.getFechaPedido() +
                        "\nCliente: " + pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellidoPaterno() +
                        "\nDirección de envío: " + pedido.getUsuario().getDireccion(),
                infoFont);
        infoPedido.setSpacingAfter(15);
        document.add(infoPedido);

        // Mostrar productos:  imagen + descripción alineada a la derecha
        Font productFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
        for (DetallePedido detalle : detallesPedido) {
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 3f});

            // Imagen del producto
            PdfPCell cellImagen = new PdfPCell();
            cellImagen.setPadding(5);
            cellImagen.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            try {
                String imagenRuta = "C:/imagenes/" + detalle.getProducto().getImagen();
                Image imagenProducto = Image.getInstance(imagenRuta);
                imagenProducto.scaleAbsolute(60, 60);
                cellImagen.addElement(imagenProducto);
            } catch (Exception e) {
                cellImagen.addElement(new Phrase("Sin imagen disponible", productFont));
            }
            table.addCell(cellImagen);

            // Información del producto alineada a la derecha
            PdfPCell cellInfo = new PdfPCell();
            cellInfo.setPadding(5);
            Paragraph productoInfo = new Paragraph(
                    "Producto: " + detalle.getProducto().getNombreProducto() +
                            "\nCantidad: " + detalle.getCantidadProductos() +
                            "\nSubtotal: $" + detalle.getSubtotal(),
                    productFont);
            productoInfo.setAlignment(Paragraph.ALIGN_LEFT);
            cellInfo.addElement(productoInfo);
            table.addCell(cellInfo);

            document.add(table);
        }

        // Total
        Paragraph totalPedido = new Paragraph("\nTotal: $" + pedido.getTotalPedido(), infoFont);
        totalPedido.setAlignment(Paragraph.ALIGN_RIGHT);
        totalPedido.setSpacingBefore(15);
        document.add(totalPedido);
    }
}
