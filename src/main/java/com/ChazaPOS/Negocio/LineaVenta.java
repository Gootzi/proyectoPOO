package ProyectoPOO.src.main.java.com.ChazaPOS.Negocio;

public class LineaVenta {
    private String nombreProducto;
    private double precio;
    private int cantidad,productoCodigo;

    public double subtotal(){
        return precio * cantidad;
    };
    public void sumarCantidad(int extra){}; 
}
