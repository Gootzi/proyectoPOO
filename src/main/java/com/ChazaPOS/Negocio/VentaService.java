package com.ChazaPOS.Negocio;

public class VentaService {
    private VentaRepositorio ventas;
    private ProductoRepositorio productos;

    public VentaService() {
    }

    public VentaService(VentaRepositorio ventas, ProductoRepositorio productos) {
        this.ventas = ventas;
        this.productos = productos;
    }

    public Venta nuevaVenta() {
        return new Venta();
    }

    public void agregarCarrito(Venta carrito, Producto p, int cantidad) {
        if (carrito != null && p != null) {
            carrito.agregarLinea(p, cantidad);
        }
    }

    public Venta confirmar(Venta carrito) {
        if (carrito != null && ventas != null) {
            return ventas.guardar(carrito);
        }
        return carrito;
    }

    public VentaRepositorio getVentas() {
        return ventas;
    }

    public void setVentas(VentaRepositorio ventas) {
        this.ventas = ventas;
    }

    public ProductoRepositorio getProductos() {
        return productos;
    }

    public void setProductos(ProductoRepositorio productos) {
        this.productos = productos;
    }
}
