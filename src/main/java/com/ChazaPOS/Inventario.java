package com.Ventana;

import java.nio.file.Path;
import java.util.List;

public class Inventario {
    
    private Path carpeta;
    private ProductoRepositorio repo;
    //private AutenticacionService sesion;

    public List<Producto> listar(){};
    public List<Producto> buscar(String texto){};
    public List<Producto> conStockBajo(){};
    public Producto guardar(Producto p, Path imagen){
        if (imagen != null){
            p.setRutaImagen(copiarImagen(imagen));
        }   
        return p;
    };
    public void desactivar(Producto p){};
    public void reponerStock(Producto p, int cantidad){};
    private void validar (Producto p){};
    private String copiarImagen(Path origen){
        //** Crear una carpeta y una copia de la imagen para tener un 
        //   backup de la imagen por si se elimina la original.*/ 
        
    };

}
