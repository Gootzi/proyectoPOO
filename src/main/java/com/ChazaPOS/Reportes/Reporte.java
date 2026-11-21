package com.ChazaPOS.Reportes;

import java.time.LocalDateTime;

public abstract class Reporte {
    protected LocalDateTime fechaGeneracion;
    protected String titulo;
    protected String encabezado;

    public Reporte(String titulo) {
        this.fechaGeneracion = LocalDateTime.now();
        this.titulo = titulo;
        this.encabezado = "=== CHAZAPOS - REPORTE DEL SISTEMA ===";
    }

    public abstract String generar();

    public void exportar(Exportador exportador, String ruta) {
        if (exportador == null) {
            throw new IllegalArgumentException("El exportador no puede ser nulo.");
        }
        exportador.exportar(generar(), ruta);
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }
}
