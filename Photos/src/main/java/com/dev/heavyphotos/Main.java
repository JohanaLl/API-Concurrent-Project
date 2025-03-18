package com.dev.heavyphotos;

import com.dev.heavyphotos.model.Photo;
import com.dev.heavyphotos.services.PhotoService;
import com.dev.heavyphotos.services.PhotoServiceImpl;

import java.util.List;

public class Main {

    /**
     * Método principal que muestra las 100 fotos más pesadas.
     *
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        try {
            // Obtener número de fotos (por defecto 100)
            int numPhotos = args.length > 0 ? Integer.parseInt(args[0]) : 100;

            // Crear servicio usando Factory Method
            PhotoService photoService = PhotoServiceImpl.createDefault();

            System.out.println("Obteniendo las " + numPhotos + " fotos más pesadas...");

            // Procesar las fotos más pesadas usando Virtual Threads
            long startTime = System.currentTimeMillis();

            List<Photo> heaviestPhotos = photoService.processHeaviestPhotosWithVirtualThreads(numPhotos);

            long endTime = System.currentTimeMillis();

            // Imprimir resultados usando programación funcional
            System.out.println("\nLas " + numPhotos + " fotos más pesadas (procesadas en " + (endTime - startTime) + "ms):");

            heaviestPhotos.stream()
                    .map(photo -> String.format("ID: %d, Título: %s, Peso: %d",
                            photo.id(), photo.title(), photo.getWeight()))
                    .forEach(System.out::println);

            // Estadísticas usando programación funcional
            double avgWeight = heaviestPhotos.stream()
                    .mapToInt(Photo::getWeight)
                    .average()
                    .orElse(0);

            int maxWeight = heaviestPhotos.stream()
                    .mapToInt(Photo::getWeight)
                    .max()
                    .orElse(0);

            int minWeight = heaviestPhotos.stream()
                    .mapToInt(Photo::getWeight)
                    .min()
                    .orElse(0);

            System.out.println("\nEstadísticas:");
            System.out.println("Peso promedio: " + avgWeight);
            System.out.println("Peso máximo: " + maxWeight);
            System.out.println("Peso mínimo: " + minWeight);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}