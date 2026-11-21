package com.ChazaPOS.Reportes;

import com.ChazaPOS.Negocio.LineaVenta;
import com.ChazaPOS.Negocio.Venta;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReporteVentas extends Reporte {
    private List<Venta> ventas;
    private double totalRecaudado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReporteVentas(List<Venta> ventas, LocalDateTime inicio, LocalDateTime fin) {
        super("Reporte de Ventas");
        this.ventas = (ventas != null) ? new ArrayList<>(ventas) : new ArrayList<>();
        this.fechaInicio = inicio;
        this.fechaFin = fin;
        this.totalRecaudado = calcularTotalRecaudado();
    }

    @Override
    public String generar() {
        StringBuilder sb = new StringBuilder();
        sb.append(encabezado).append("\n");
        sb.append("TÍTULO: ").append(titulo).append("\n");
        sb.append("Fecha de generación: ").append(fechaGeneracion.format(FORMATO_FECHA)).append("\n");
        sb.append("Período evaluado: ")
          .append(fechaInicio != null ? fechaInicio.format(FORMATO_FECHA) : "Inicio no definido")
          .append(" hasta ")
          .append(fechaFin != null ? fechaFin.format(FORMATO_FECHA) : "Fin no definido")
          .append("\n");
        sb.append("------------------------------------------------------------\n");
        sb.append("RESUMEN DE VENTAS\n");
        sb.append("Total de transacciones: ").append(contarTotalTransacciones()).append("\n");
        sb.append(String.format("Total recaudado: $%,.2f\n", calcularTotalRecaudado()));
        sb.append("------------------------------------------------------------\n");
        sb.append("DETALLE DE TRANSACCIONES:\n");

        if (ventas.isEmpty()) {
            sb.append("No se registraron ventas en el período especificado.\n");
        } else {
            int indice = 1;
            for (Venta v : ventas) {
                sb.append(String.format("Venta #%d - Total: $%,.2f\n", indice++, v.total()));
                if (v.getLineas() != null && !v.getLineas().isEmpty()) {
                    for (LineaVenta linea : v.getLineas()) {
                        sb.append(String.format("   • %s x%d - Subtotal: $%,.2f\n",
                                linea.getNombreProducto() != null ? linea.getNombreProducto() : "Producto",
                                linea.getCantidad(),
                                linea.subtotal()));
                    }
                }
            }
        }
        sb.append("============================================================\n");
        return sb.toString();
    }

    public double calcularTotalRecaudado() {
        double total = 0.0;
        if (ventas != null) {
            for (Venta v : ventas) {
                if (v != null) {
                    total += v.total();
                }
            }
        }
        this.totalRecaudado = total;
        return total;
    }

    public int contarTotalTransacciones() {
        return ventas != null ? ventas.size() : 0;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = (ventas != null) ? new ArrayList<>(ventas) : new ArrayList<>();
        this.totalRecaudado = calcularTotalRecaudado();
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}
