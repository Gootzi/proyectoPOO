package com.ChazaPOS.Reportes;

import com.ChazaPOS.Negocio.LineaVenta;
import com.ChazaPOS.Negocio.Producto;
import com.ChazaPOS.Negocio.ProductoRepositorio;
import com.ChazaPOS.Negocio.Venta;
import com.ChazaPOS.Negocio.VentaRepositorio;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime; //Puede ser util
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EstadisticasService {
    private VentaRepositorio ventaRepositorio;
    private ProductoRepositorio productoRepositorio;

    public EstadisticasService(VentaRepositorio ventaRepositorio, ProductoRepositorio productoRepositorio) {
        this.ventaRepositorio = ventaRepositorio;
        this.productoRepositorio = productoRepositorio;
    }

    public Producto obtenerProductoMasVendido() {
        List<Producto> top = obtenerTopProductos(1);
        return top.isEmpty() ? null : top.get(0);
    }

    public double calcularVentasDelDia() {
        if (ventaRepositorio == null) {
            return 0.0;
        }

        LocalDateTime hoyInicio = LocalDate.now().atStartOfDay();
        LocalDateTime hoyFin = LocalDateTime.now();

        List<Venta> ventasHoy = ventaRepositorio.buscarFecha(hoyInicio, hoyFin);
        double total = 0.0;
        if (ventasHoy != null) {
            for (Venta v : ventasHoy) {
                if (v != null) {
                    total += v.total();
                }
            }
        }
        return total;
    }

    public List<Producto> obtenerTopProductos(int limite) {
        if (ventaRepositorio == null || productoRepositorio == null || limite <= 0) {
            return Collections.emptyList();
        }

        // Consultar histórico de ventas disponible
        LocalDateTime desde = LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime hasta = LocalDateTime.now();
        List<Venta> ventas = ventaRepositorio.buscarFecha(desde, hasta);

        if (ventas == null || ventas.isEmpty()) {
            return Collections.emptyList();
        }

        // Conteo de unidades vendidas por código de producto
        Map<Integer, Integer> conteoPorCodigo = new HashMap<>();
        for (Venta v : ventas) {
            if (v != null && v.getLineas() != null) {
                for (LineaVenta linea : v.getLineas()) {
                    if (linea != null) {
                        int codigo = linea.getProductoCodigo();
                        int cantidad = linea.getCantidad();
                        conteoPorCodigo.put(codigo, conteoPorCodigo.getOrDefault(codigo, 0) + cantidad);
                    }
                }
            }
        }

        // Ordenar productos por cantidad vendida descendente
        List<Map.Entry<Integer, Integer>> ordenados = new ArrayList<>(conteoPorCodigo.entrySet());
        ordenados.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        List<Producto> topProductos = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : ordenados) {
            if (count >= limite) break;
            Optional<Producto> prodOpt = productoRepositorio.buscarCodigo(entry.getKey());
            if (prodOpt.isPresent()) {
                topProductos.add(prodOpt.get());
                count++;
            }
        }

        return topProductos;
    }

    public TablaDatos generarTablaConsolidada() {
        List<String> columnas = Arrays.asList(
                "Código", "Producto", "Categoría", "Stock Actual", "Mínimo", "Estado"
        );
        TablaDatos tabla = new TablaDatos(columnas);

        if (productoRepositorio != null) {
            List<Producto> productos = productoRepositorio.listar(true);
            if (productos != null) {
                for (Producto p : productos) {
                    if (p != null) {
                        String estado;
                        if (p.getStock() <= 0) {
                            estado = "Agotado";
                        } else if (p.getStock() <= p.getStockMinimo()) {
                            estado = "Stock Bajo";
                        } else {
                            estado = "Disponible";
                        }

                        List<Object> fila = Arrays.asList(
                                p.getCodigo(),
                                p.getNombre(),
                                p.getCategoria(),
                                p.getStock(),
                                p.getStockMinimo(),
                                estado
                        );
                        tabla.agregarFila(fila);
                    }
                }
            }
        }

        return tabla;
    }

    /**
     * Utiliza la dependencia con Exportador para persistir una tabla en archivo.
     */
    public boolean exportarTabla(TablaDatos tabla, Exportador exportador, String rutaArchivo) {
        if (tabla == null || exportador == null) {
            return false;
        }

        if (exportador instanceof ExportadorCSV) {
            return ((ExportadorCSV) exportador).exportarTabla(tabla, rutaArchivo);
        } else {
            return exportador.exportar(tabla.formatearTextoPlano(), rutaArchivo);
        }
    }

    public VentaRepositorio getVentaRepositorio() {
        return ventaRepositorio;
    }

    public void setVentaRepositorio(VentaRepositorio ventaRepositorio) {
        this.ventaRepositorio = ventaRepositorio;
    }

    public ProductoRepositorio getProductoRepositorio() {
        return productoRepositorio;
    }

    public void setProductoRepositorio(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }
}
