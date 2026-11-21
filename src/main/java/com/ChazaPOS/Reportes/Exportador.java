package com.ChazaPOS.Reportes;

public interface Exportador {
    /**
     * Exporta el contenido proporcionado a la ruta de archivo especificada.
     *
     * @param contenido   Texto o contenido estructurado a exportar.
     * @param rutaArchivo Ruta absoluta o relativa del archivo de destino.
     * @return true si la exportación fue exitosa, false en caso de error.
     */
    boolean exportar(String contenido, String rutaArchivo);
}
