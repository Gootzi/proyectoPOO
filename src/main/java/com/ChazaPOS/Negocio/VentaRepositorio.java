package com.ChazaPOS.Negocio;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepositorio {
    Venta guardar(Venta v);
    List<Venta> buscarFecha(LocalDateTime desde, LocalDateTime hasta);
}
