package com.ChazaPOS.Reportes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TablaDatos {
    private List<String> columnas;
    private List<List<Object>> filas;

    public TablaDatos(List<String> columnas) {
        this.columnas = (columnas != null) ? new ArrayList<>(columnas) : new ArrayList<>();
        this.filas = new ArrayList<>();
    }

    public void agregarFila(List<Object> fila) {
        if (fila != null) {
            this.filas.add(new ArrayList<>(fila));
        }
    }

    public Object[][] getMatriz() {
        int numFilas = filas.size();
        int numColumnas = columnas.size();
        Object[][] matriz = new Object[numFilas][numColumnas];

        for (int i = 0; i < numFilas; i++) {
            List<Object> fila = filas.get(i);
            for (int j = 0; j < numColumnas; j++) {
                matriz[i][j] = (j < fila.size()) ? fila.get(j) : "";
            }
        }
        return matriz;
    }

    public String formatearTextoPlano() {
        if (columnas.isEmpty() && filas.isEmpty()) {
            return "(Tabla vacía)";
        }

        int numColumnas = columnas.size();
        int[] anchos = new int[numColumnas];

        for (int j = 0; j < numColumnas; j++) {
            anchos[j] = columnas.get(j) != null ? columnas.get(j).length() : 0;
        }

        for (List<Object> fila : filas) {
            for (int j = 0; j < numColumnas; j++) {
                if (j < fila.size() && fila.get(j) != null) {
                    anchos[j] = Math.max(anchos[j], String.valueOf(fila.get(j)).length());
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        // Línea separadora
        String separador = construirSeparador(anchos);
        sb.append(separador).append("\n");

        // Cabeceras
        sb.append("|");
        for (int j = 0; j < numColumnas; j++) {
            String col = columnas.get(j) != null ? columnas.get(j) : "";
            sb.append(" ").append(padRight(col, anchos[j])).append(" |");
        }
        sb.append("\n");
        sb.append(separador).append("\n");

        // Filas
        for (List<Object> fila : filas) {
            sb.append("|");
            for (int j = 0; j < numColumnas; j++) {
                String val = (j < fila.size() && fila.get(j) != null) ? String.valueOf(fila.get(j)) : "";
                sb.append(" ").append(padRight(val, anchos[j])).append(" |");
            }
            sb.append("\n");
        }

        sb.append(separador).append("\n");
        return sb.toString();
    }

    private String construirSeparador(int[] anchos) {
        StringBuilder sb = new StringBuilder("+");
        for (int ancho : anchos) {
            sb.append("-".repeat(ancho + 2)).append("+");
        }
        return sb.toString();
    }

    private String padRight(String texto, int longitud) {
        if (texto.length() >= longitud) {
            return texto;
        }
        return texto + " ".repeat(longitud - texto.length());
    }

    public List<String> getColumnas() {
        return Collections.unmodifiableList(columnas);
    }

    public List<List<Object>> getFilas() {
        return Collections.unmodifiableList(filas);
    }
}
