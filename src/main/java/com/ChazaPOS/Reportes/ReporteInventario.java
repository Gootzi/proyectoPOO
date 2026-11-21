package com.ChazaPOS.Reportes;

import com.ChazaPOS.Negocio.Producto;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReporteInventario extends Reporte {
    private List<Producto> productos;
    private int limiteStockBajo;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReporteInventario(List<Producto> productos) {
        super("Reporte de Inventario");
        this.productos = (productos != null) ? new ArrayList<>(productos) : new ArrayList<>();
        this.limiteStockBajo = 3;
    }

    @Override
    public String generar() {
        StringBuilder sb = new StringBuilder();
        sb.append(encabezado).append("\n");
        sb.append("TÍTULO: ").append(titulo).append("\n");
        sb.append("Fecha de generación: ").append(fechaGeneracion.format(FORMATO_FECHA)).append("\n");
        sb.append("Umbral de stock bajo considerado: ").append(limiteStockBajo).append(" unidades\n");
        sb.append("----------------------------------------------------------------------------------------\n");

        List<Producto> agotados = obtenerProductosAgotados();
        List<Producto> stockBajo = obtenerProductosStockBajo();

        sb.append("RESUMEN DE INVENTARIO\n");
        sb.append("Total de productos registrados: ").append(productos.size()).append("\n");
        sb.append("Productos agotados (sin stock): ").append(agotados.size()).append("\n");
        sb.append("Productos con stock bajo (crítico): ").append(stockBajo.size()).append("\n");
        sb.append("----------------------------------------------------------------------------------------\n");

        sb.append("DETALLE DE PRODUCTOS:\n");
        sb.append(String.format("%-8s | %-25s | %-15s | %-7s | %-10s | %-10s\n",
                "CÓDIGO", "NOMBRE", "CATEGORÍA", "STOCK", "MÍNIMO", "ESTADO"));
        sb.append("----------------------------------------------------------------------------------------\n");

        if (productos.isEmpty()) {
            sb.append("No hay productos registrados en el inventario.\n");
        } else {
            for (Producto p : productos) {
                String estado;
                if (p.getStock() <= 0) {
                    estado = "AGOTADO";
                } else if (p.getStock() <= limiteStockBajo) {
                    estado = "STOCK BAJO";
                } else {
                    estado = "NORMAL";
                }

                sb.append(String.format("%-8d | %-25s | %-15s | %-7d | %-10d | %-10s\n",
                        p.getCodigo(),
                        truncar(p.getNombre(), 25),
                        truncar(p.getCategoria(), 15),
                        p.getStock(),
                        p.getStockMinimo(),
                        estado));
            }
        }

        if (!agotados.isEmpty()) {
            sb.append("\n¡ALERTA! PRODUCTOS QUE REQUIEREN REABASTECIMIENTO INMEDIATO:\n");
            for (Producto p : agotados) {
                sb.append(String.format("   [!] Código: %d - %s (Stock actual: 0)\n", p.getCodigo(), p.getNombre()));
            }
        }

        sb.append("========================================================================================\n");
        return sb.toString();
    }

    public List<Producto> obtenerProductosAgotados() {
        List<Producto> agotados = new ArrayList<>();
        if (productos != null) {
            for (Producto p : productos) {
                if (p != null && p.getStock() <= 0) {
                    agotados.add(p);
                }
            }
        }
        return agotados;
    }

    public List<Producto> obtenerProductosStockBajo() {
        List<Producto> bajo = new ArrayList<>();
        if (productos != null) {
            for (Producto p : productos) {
                if (p != null && p.getStock() > 0 && p.getStock() <= limiteStockBajo) {
                    bajo.add(p);
                }
            }
        }
        return bajo;
    }

    private String truncar(String texto, int longitudMaxima) {
        if (texto == null) return "";
        if (texto.length() <= longitudMaxima) return texto;
        return texto.substring(0, longitudMaxima - 3) + "...";
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = (productos != null) ? new ArrayList<>(productos) : new ArrayList<>();
    }

    public int getLimiteStockBajo() {
        return limiteStockBajo;
    }

    public void setLimiteStockBajo(int limiteStockBajo) {
        this.limiteStockBajo = limiteStockBajo;
    }
}
