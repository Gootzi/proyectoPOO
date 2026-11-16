package com.Ventana;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Venta {
    private int codigo;
    private LocalDate fechaHora;
    private Usuario vendedor;
    private List<LineaVenta> lineas;

    public void agregarLinea (Producto p, int cantidad){};
    public double total (){};
    public List<LineaVenta> getLineas(){};
    public void cargarLineas(LineaVenta l){};
}
