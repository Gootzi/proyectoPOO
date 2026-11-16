package ProyectoPOO.src.main.java.com.ChazaPOS.Negocio;

import java.time.LocalDateTime;

public interface VentaRepositorio {
    public Venta guardar(Venta v){};
    public List<Venta> buscarFecha(LocalDateTime desde, LocalDateTime hasta){};
}
