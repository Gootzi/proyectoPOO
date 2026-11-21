package com.ChazaPOS.Reportes;

import com.ChazaPOS.Negocio.LineaVenta;
import com.ChazaPOS.Negocio.Venta;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatoRecibo {
    private Venta venta;
    private String nombreChaza;
    private String mensajePie;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public FormatoRecibo(Venta venta) {
        this.venta = venta;
        this.nombreChaza = "LA CHAZA UNIVERSITARIA";
        this.mensajePie = "¡Gracias por su compra!\nConserve este recibo como soporte.";
    }

    public String generarTextoRecibo() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append(centrarTexto(nombreChaza, 40)).append("\n");
        sb.append(centrarTexto("SISTEMA DE FACTURACIÓN POS", 40)).append("\n");
        sb.append("========================================\n");
        sb.append("Fecha/Hora : ").append(LocalDateTime.now().format(FORMATO_FECHA)).append("\n");
        sb.append("----------------------------------------\n");
        sb.append(String.format("%-18s %4s %7s %8s\n", "ITEM", "CANT", "PRECIO", "TOTAL"));
        sb.append("----------------------------------------\n");

        if (venta != null && venta.getLineas() != null && !venta.getLineas().isEmpty()) {
            for (LineaVenta linea : venta.getLineas()) {
                sb.append(formatearLinea(linea)).append("\n");
            }
        } else {
            sb.append("   (Sin productos registrados)\n");
        }

        sb.append("----------------------------------------\n");
        double total = (venta != null) ? venta.total() : 0.0;
        sb.append(String.format("TOTAL A PAGAR: %25s\n", String.format("$%,.2f", total)));
        sb.append("========================================\n");
        sb.append(centrarTexto(mensajePie, 40)).append("\n");
        sb.append("========================================\n");

        return sb.toString();
    }

    public String formatearLinea(LineaVenta linea) {
        if (linea == null) {
            return "";
        }
        String nombre = linea.getNombreProducto() != null ? linea.getNombreProducto() : "Item";
        if (nombre.length() > 18) {
            nombre = nombre.substring(0, 15) + "...";
        }
        return String.format("%-18s %4d %7.2f %8.2f",
                nombre,
                linea.getCantidad(),
                linea.getPrecio(),
                linea.subtotal());
    }

    private String centrarTexto(String texto, int ancho) {
        if (texto == null) return "";
        String[] lineas = texto.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lineas.length; i++) {
            String l = lineas[i];
            int padding = Math.max(0, (ancho - l.length()) / 2);
            sb.append(" ".repeat(padding)).append(l);
            if (i < lineas.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public String getNombreChaza() {
        return nombreChaza;
    }

    public void setNombreChaza(String nombreChaza) {
        this.nombreChaza = nombreChaza;
    }

    public String getMensajePie() {
        return mensajePie;
    }

    public void setMensajePie(String mensajePie) {
        this.mensajePie = mensajePie;
    }
}
