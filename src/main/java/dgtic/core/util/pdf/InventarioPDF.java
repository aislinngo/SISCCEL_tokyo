package dgtic.core.util.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import dgtic.core.model.producto.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.Color;
import java.util.List;
import java.util.Map;

@Component("inventario-pdf")
public class InventarioPDF extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        // El PDF se muestre en pantalla (inline), si cambiamos por Attatchment se descarga.
        response.setHeader("Content-Disposition", "inline; filename=\"inventario.pdf\"");

        // Recuperar la lista de productos y el mapa de existencias desde el modelo
        @SuppressWarnings("unchecked")
        List<Producto> productos = (List<Producto>) model.get("productos");
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> inventoryMap = (Map<Integer, Integer>) model.get("inventoryMap");

        // Agregar el logo al pdf
        try {
            String logoPath = "C:/imagenes/flor-cerezo.png";
            Image logo = Image.getInstance(logoPath);
            logo.scaleAbsolute(60, 60);  // Ajusta tamaño
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            // En caso de error, se ignora y continúa sin logo.
        }

        // Título del documento
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
        Paragraph title = new Paragraph("Reporte de Inventario", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Crear la tabla con 6 columnas: Imagen, Producto, Descripción, SKU, Categoría, Existencias Actuales
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[]{2f, 4f, 5f, 3f, 3f, 2f});

        // Agregar encabezados de la tabla
        table.addCell(crearCeldaEncabezado("Imagen"));
        table.addCell(crearCeldaEncabezado("Producto"));
        table.addCell(crearCeldaEncabezado("Descripción"));
        table.addCell(crearCeldaEncabezado("SKU"));
        table.addCell(crearCeldaEncabezado("Categoría"));
        table.addCell(crearCeldaEncabezado("Existencias Actual"));

        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

        // Recorrer la lista de productos para agregar filas a la tabla
        for (Producto producto : productos) {
            // Columna para la imagen del producto
            PdfPCell cellImagen = new PdfPCell();
            cellImagen.setPadding(5);
            cellImagen.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            try {
                // Construir la ruta completa de la imagen del producto.
                String imagenRuta = "C:/imagenes/" + producto.getImagen();
                Image imagenProducto = Image.getInstance(imagenRuta);
                imagenProducto.scaleAbsolute(50, 50);
                cellImagen.addElement(imagenProducto);
            } catch (Exception e) {
                // Si no se puede cargar la imagen, se agrega un texto alternativo.
                cellImagen.addElement(new Phrase("Sin imagen", dataFont));
            }
            table.addCell(cellImagen);

            // Celda para el nombre del producto
            PdfPCell cellProducto = new PdfPCell(new Phrase(producto.getNombreProducto(), dataFont));
            cellProducto.setPadding(5);
            table.addCell(cellProducto);

            // Celda para la descripción
            PdfPCell cellDesc = new PdfPCell(new Phrase(producto.getDescripcion(), dataFont));
            cellDesc.setPadding(5);
            table.addCell(cellDesc);

            // Celda para el SKU
            PdfPCell cellSku = new PdfPCell(new Phrase(producto.getSku(), dataFont));
            cellSku.setPadding(5);
            table.addCell(cellSku);

            // Celda para la categoría
            PdfPCell cellCat = new PdfPCell(new Phrase(producto.getCategoria().getNombreCategoria(), dataFont));
            cellCat.setPadding(5);
            table.addCell(cellCat);

            // Celda para las existencias actuales, se pinta de rojo si es 0
            Integer existencias = inventoryMap.get(producto.getIdProducto());
            String existStr = (existencias == null) ? "0" : existencias.toString();
            PdfPCell cellExist = new PdfPCell(new Phrase(existStr, dataFont));
            cellExist.setPadding(5);
            if (existencias == null || existencias == 0) {
                cellExist.setBackgroundColor(Color.RED);
            }
            table.addCell(cellExist);
        }
        document.add(table);
    }

    // Metodo auxiliar para crear una celda de encabezado con fondo rosa y texto blanco
    private PdfPCell crearCeldaEncabezado(String texto) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(texto, headerFont));
        cell.setBackgroundColor(Color.PINK);
        cell.setPadding(8);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }
}
