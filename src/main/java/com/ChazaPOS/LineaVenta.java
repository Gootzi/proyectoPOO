package com.Ventana;

public class LineaVenta {
    private String nombreProducto;
    private double precio;
    private int cantidad,productoCodigo;

    public double subtotal(){
        return precio * cantidad;
    };
    public void sumarCantidad(int extra){}; 
}
