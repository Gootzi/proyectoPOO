package com.ChazaPOS.Negocio;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private Path carpeta;
    private ProductoRepositorio repo;

    public Inventario() {
    }

    public Inventario(Path carpeta, ProductoRepositorio repo) {
        this.carpeta = carpeta;
        this.repo = repo;
    }

    public List<Producto> listar() {
        return (repo != null) ? repo.listar(true) : new ArrayList<>();
    }

    public List<Producto> buscar(String texto) {
        return (repo != null) ? repo.buscarNombre(texto) : new ArrayList<>();
    }

    public List<Producto> conStockBajo() {
        List<Producto> bajo = new ArrayList<>();
        if (repo != null) {
            for (Producto p : repo.listar(true)) {
                if (p.stockBajo()) {
                    bajo.add(p);
                }
            }
        }
        return bajo;
    }

    public Producto guardar(Producto p, Path imagen) {
        if (imagen != null) {
            p.setRutaImagen(copiarImagen(imagen));
        }
        if (repo != null) {
            return repo.guardar(p);
        }
        return p;
    }

    public void desactivar(Producto p) {
        if (p != null) {
            p.setActivo(false);
            if (repo != null) {
                repo.guardar(p);
            }
        }
    }

    public void reponerStock(Producto p, int cantidad) {
        if (p != null && cantidad > 0) {
            p.setStock(p.getStock() + cantidad);
            if (repo != null) {
                repo.guardar(p);
            }
        }
    }

    private void validar(Producto p) {
        if (p == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
    }

    private String copiarImagen(Path origen) {
        // Crear una carpeta y una copia de la imagen para tener un
        // backup de la imagen por si se elimina la original.
        return (origen != null) ? origen.toString() : "";
    }

    public Path getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(Path carpeta) {
        this.carpeta = carpeta;
    }

    public ProductoRepositorio getRepo() {
        return repo;
    }

    public void setRepo(ProductoRepositorio repo) {
        this.repo = repo;
    }
}
