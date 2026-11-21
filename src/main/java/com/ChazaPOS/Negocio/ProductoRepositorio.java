package com.ChazaPOS.Negocio;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositorio {
    Optional<Producto> buscarCodigo(int codigo);
    List<Producto> buscarNombre(String nombre);
    List<Producto> listar(boolean activos);
    Producto guardar(Producto p);
}
