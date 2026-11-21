package com.ChazaPOS.Negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int codigo;
    private LocalDateTime fechaHora;
    private Usuario vendedor;
    private List<LineaVenta> lineas;

    public Venta() {
        this.fechaHora = LocalDateTime.now();
        this.lineas = new ArrayList<>();
    }

    public Venta(int codigo, LocalDateTime fechaHora, Usuario vendedor) {
        this.codigo = codigo;
        this.fechaHora = fechaHora != null ? fechaHora : LocalDateTime.now();
        this.vendedor = vendedor;
        this.lineas = new ArrayList<>();
    }

    public void agregarLinea(Producto p, int cantidad) {
        if (p != null && cantidad > 0) {
            if (lineas == null) {
                lineas = new ArrayList<>();
            }
            LineaVenta linea = new LineaVenta(p.getCodigo(), p.getNombre(), 0.0, cantidad);
            lineas.add(linea);
        }
    }

    public void cargarLineas(LineaVenta l) {
        if (lineas == null) {
            lineas = new ArrayList<>();
        }
        if (l != null) {
            lineas.add(l);
        }
    }

    public double total() {
        double total = 0.0;
        if (lineas != null) {
            for (LineaVenta l : lineas) {
                if (l != null) {
                    total += l.subtotal();
                }
            }
        }
        return total;
    }

    public List<LineaVenta> getLineas() {
        if (lineas == null) {
            lineas = new ArrayList<>();
        }
        return lineas;
    }

    public void setLineas(List<LineaVenta> lineas) {
        this.lineas = lineas != null ? lineas : new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }
}
