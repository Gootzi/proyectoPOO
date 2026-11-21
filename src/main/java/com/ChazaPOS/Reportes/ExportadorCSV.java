package com.ChazaPOS.Reportes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExportadorCSV implements Exportador {

    @Override
    public boolean exportar(String contenido, String rutaArchivo) {
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            System.err.println("Error al exportar CSV: la ruta del archivo no es válida.");
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
            System.err.println("Error al escribir el archivo CSV en " + rutaArchivo + ": " + e.getMessage());
            return false;
        }
    }

    public String convertirTablaACSV(TablaDatos tabla) {
        if (tabla == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        List<String> columnas = tabla.getColumnas();

        // Cabeceras de columnas
        for (int i = 0; i < columnas.size(); i++) {
            sb.append(escaparValorCSV(columnas.get(i)));
            if (i < columnas.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("\n");

        // Filas de datos
        for (List<Object> fila : tabla.getFilas()) {
            for (int i = 0; i < columnas.size(); i++) {
                Object valor = (i < fila.size()) ? fila.get(i) : "";
                sb.append(escaparValorCSV(valor));
                if (i < columnas.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean exportarTabla(TablaDatos tabla, String rutaArchivo) {
        return exportar(convertirTablaACSV(tabla), rutaArchivo);
    }

    private String escaparValorCSV(Object valor) {
        if (valor == null) {
            return "";
        }
        String texto = String.valueOf(valor);
        boolean requiereComillas = texto.contains(",") || texto.contains("\"") || texto.contains("\n") || texto.contains("\r");

        if (requiereComillas) {
            texto = texto.replace("\"", "\"\"");
            return "\"" + texto + "\"";
        }
        return texto;
    }
}
