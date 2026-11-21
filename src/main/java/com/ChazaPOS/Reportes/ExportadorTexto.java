package com.ChazaPOS.Reportes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExportadorTexto implements Exportador {

    @Override
    public boolean exportar(String contenido, String rutaArchivo) {
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            System.err.println("Error al exportar texto: la ruta del archivo no es válida.");
            return false;
        }

        try {
            Path path = Paths.get(rutaArchivo);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            Files.writeString(path, contenido != null ? contenido : "", StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de texto en " + rutaArchivo + ": " + e.getMessage());
            return false;
        }
    }
}
