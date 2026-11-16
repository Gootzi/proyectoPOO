package ProyectoPOO.src.main.java.com.ChazaPOS.Negocio;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositorio {
    public Optional<Producto> buscarCodigo(int codigo){};
    public List<Producto> buscarNombre(String nombre){};
    public List<Producto> listar(boolean activos){};
    public Producto guardar(Producto p){};
}
