package com.ChazaPOS.Negocio;

public class LineaVenta {
    private String nombreProducto;
    private double precio;
    private int cantidad;
    private int productoCodigo;

    public LineaVenta() {
    }

    public LineaVenta(int productoCodigo, String nombreProducto, double precio, int cantidad) {
        this.productoCodigo = productoCodigo;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public double subtotal() {
        return precio * cantidad;
    }

    public void sumarCantidad(int extra) {
        this.cantidad += extra;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getProductoCodigo() {
        return productoCodigo;
    }

    public void setProductoCodigo(int productoCodigo) {
        this.productoCodigo = productoCodigo;
    }
}
