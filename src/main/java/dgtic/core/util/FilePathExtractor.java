package dgtic.core.util;

import java.io.File;

public class FilePathExtractor {

    public static void main(String[] args) {
        // Define el directorio raíz desde donde deseas extraer las rutas
        String rootDirectory = "src"; // cambia a "src" para todo el proyecto

        File directory = new File(rootDirectory);
        if (directory.exists() && directory.isDirectory()) {
            printFilePaths(directory);
        } else {
            System.out.println("El directorio especificado no existe o no es válido.");
        }
    }

    private static void printFilePaths(File directory) {
        // Recorre todos los archivos y subdirectorios dentro del directorio especificado
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Si es un subdirectorio, llama recursivamente
                    printFilePaths(file);
                } else {
                    // Si es un archivo, imprime su ruta
                    System.out.println(file.getPath());
                }
            }
        }
    }
}
