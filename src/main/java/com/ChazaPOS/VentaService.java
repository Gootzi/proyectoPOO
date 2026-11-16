package ProyectoPOO.src.main.java.com.ChazaPOS;

public class VentaService implements ProductoRepositorio,VentaRepositorio {
    private VentaRepositorio ventas;
    private ProductoRepositorio productos;

    public Venta nuevaVenta(){
        Venta venta = new Venta();
        return venta;
    };
    public void agregarCarrito(Venta carrito, Producto p,int cantidad){};
    public Venta confirmar(Venta carrito){
        Venta venta = new Venta();
        return venta;
    };
}
