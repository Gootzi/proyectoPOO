package com.ChazaPOS.Negocio;

import com.ChazaPOS.Reportes.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    // Clase concreta ficticia para probar Producto ya que es abstracta
    static class ProductoSimple extends Producto {
        public ProductoSimple(int codigo, int stock, int stockMinimo, String nombre, String categoria, String rutaImagen, boolean activo) {
            super(codigo, stock, stockMinimo, nombre, categoria, rutaImagen, activo);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBAS DE MÓDULO REPORTES ===");

        // 1. Crear datos de prueba
        Producto p1 = new ProductoSimple(1, 10, 3, "Empanada de Carne", "Alimentos", "/img/emp.png", true);
        Producto p2 = new ProductoSimple(2, 2, 5, "Jugo Natural 300ml", "Bebidas", "/img/jugo.png", true);
        Producto p3 = new ProductoSimple(3, 0, 2, "Café Americano", "Bebidas", "/img/cafe.png", true);

        List<Producto> catalogo = Arrays.asList(p1, p2, p3);

        // Crear líneas de venta y ventas
        LineaVenta lv1 = new LineaVenta(1, "Empanada de Carne", 3000.0, 2);
        LineaVenta lv2 = new LineaVenta(2, "Jugo Natural 300ml", 3500.0, 1);

        Venta v1 = new Venta();
        v1.setCodigo(1001);
        v1.setFechaHora(LocalDateTime.now().minusHours(2));
        v1.cargarLineas(lv1);
        v1.cargarLineas(lv2);

        LineaVenta lv3 = new LineaVenta(1, "Empanada de Carne", 3000.0, 3);
        Venta v2 = new Venta();
        v2.setCodigo(1002);
        v2.setFechaHora(LocalDateTime.now().minusMinutes(30));
        v2.cargarLineas(lv3);

        List<Venta> listaVentas = Arrays.asList(v1, v2);

        // 2. Probar ReporteVentas y ExportadorTexto
        System.out.println("\n--- Probando ReporteVentas ---");
        ReporteVentas reporteVentas = new ReporteVentas(
                listaVentas,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        );
        String textoVentas = reporteVentas.generar();
        System.out.println(textoVentas);

        ExportadorTexto expTexto = new ExportadorTexto();
        boolean resVentasTxt = expTexto.exportar(textoVentas, "salida_reportes/reporte_ventas.txt");
        System.out.println("Exportación reporte_ventas.txt: " + (resVentasTxt ? "EXITOSA" : "FALLIDA"));

        // 3. Probar ReporteInventario
        System.out.println("\n--- Probando ReporteInventario ---");
        ReporteInventario reporteInv = new ReporteInventario(catalogo);
        String textoInv = reporteInv.generar();
        System.out.println(textoInv);

        boolean resInvTxt = expTexto.exportar(textoInv, "salida_reportes/reporte_inventario.txt");
        System.out.println("Exportación reporte_inventario.txt: " + (resInvTxt ? "EXITOSA" : "FALLIDA"));

        // 4. Probar FormatoRecibo
        System.out.println("\n--- Probando FormatoRecibo ---");
        FormatoRecibo recibo = new FormatoRecibo(v1);
        recibo.setNombreChaza("Chaza El Estudiante");
        String textoRecibo = recibo.generarTextoRecibo();
        System.out.println(textoRecibo);
        expTexto.exportar(textoRecibo, "salida_reportes/recibo_1001.txt");

        // 5. Probar TablaDatos y ExportadorCSV
        System.out.println("\n--- Probando TablaDatos y ExportadorCSV ---");
        TablaDatos tabla = new TablaDatos(Arrays.asList("ID", "Nombre, Variante", "Stock", "Precio Base", "Observación"));
        tabla.agregarFila(Arrays.asList(1, "Empanada \"Especial\", Carne", 10, 3000.0, "Crujiente\ny caliente"));
        tabla.agregarFila(Arrays.asList(2, "Jugo Natural", 2, 3500.0, "Fría"));
        tabla.agregarFila(Arrays.asList(3, "Café Americano", 0, 2000.0, "Agotado"));

        System.out.println("Tabla en texto plano:");
        System.out.println(tabla.formatearTextoPlano());

        ExportadorCSV expCsv = new ExportadorCSV();
        String csvContenido = expCsv.convertirTablaACSV(tabla);
        System.out.println("Contenido CSV generado:\n" + csvContenido);

        boolean resCsv = expCsv.exportar(csvContenido, "salida_reportes/productos.csv");
        System.out.println("Exportación productos.csv: " + (resCsv ? "EXITOSA" : "FALLIDA"));

        // 6. Probar EstadisticasService
        System.out.println("\n--- Probando EstadisticasService ---");
        VentaRepositorio repoVentasMock = new VentaRepositorio() {
            @Override
            public Venta guardar(Venta v) { return v; }

            @Override
            public List<Venta> buscarFecha(LocalDateTime desde, LocalDateTime hasta) {
                return listaVentas;
            }
        };

        ProductoRepositorio repoProdMock = new ProductoRepositorio() {
            @Override
            public Optional<Producto> buscarCodigo(int codigo) {
                return catalogo.stream().filter(p -> p.getCodigo() == codigo).findFirst();
            }

            @Override
            public List<Producto> buscarNombre(String nombre) {
                return new ArrayList<>();
            }

            @Override
            public List<Producto> listar(boolean activos) {
                return catalogo;
            }

            @Override
            public Producto guardar(Producto p) { return p; }
        };

        EstadisticasService stats = new EstadisticasService(repoVentasMock, repoProdMock);
        System.out.printf("Ventas del día: $%,.2f\n", stats.calcularVentasDelDia());

        Producto topProd = stats.obtenerProductoMasVendido();
        if (topProd != null) {
            System.out.println("Producto más vendido: " + topProd.getNombre() + " (Código: " + topProd.getCodigo() + ")");
        }

        TablaDatos consolidada = stats.generarTablaConsolidada();
        System.out.println("\nTabla consolidada de estadísticas:");
        System.out.println(consolidada.formatearTextoPlano());

        boolean resConsolidadaCsv = stats.exportarTabla(consolidada, expCsv, "salida_reportes/consolidado_stats.csv");
        System.out.println("Exportación consolidado_stats.csv: " + (resConsolidadaCsv ? "EXITOSA" : "FALLIDA"));

        System.out.println("\n=== TODAS LAS PRUEBAS COMPLETADAS EXITOSAMENTE ===");
    }
}
