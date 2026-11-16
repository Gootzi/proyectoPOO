package ProyectoPOO.src.main.java.com.ChazaPOS;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Venta {
    private int codigo;
    private LocalDate fechaHora;
    private Usuario vendedor;
    private List<LineaVenta> lineas;

    public void agregarLinea (Producto p, int cantidad){
        LineaVenta linea = new LineaVenta();
        lineas.add(linea);
    };
    public double total (){
        double total;
        for(LineaVenta l: lineas){
            total += l.subtotal();
        }
    };
    public List<LineaVenta> getLineas(){};
    public void cargarLineas(LineaVenta l){};
}
