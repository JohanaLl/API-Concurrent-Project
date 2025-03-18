package com.dev.heavyphotos.services;

import com.dev.heavyphotos.model.Photo;
import com.dev.heavyphotos.repository.PhotoRepository;
import com.dev.heavyphotos.repository.PhotoRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Implementación del servicio de fotos.
 * Utiliza Virtual Threads para el procesamiento concurrente.
 */
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    /**
     * Constructor que implementa Dependency Injection.
     *
     * @param photoRepository El repositorio de fotos a utilizar
     */
    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    /**
     * Factory method para crear una instancia con el repositorio por defecto.
     *
     * @return Una nueva instancia del servicio
     */
    public static PhotoServiceImpl createDefault() {
        return new PhotoServiceImpl(PhotoRepositoryImpl.createDefault());
    }

    @Override
    public CompletableFuture<List<Photo>> getHeaviestPhotos(int n) {
        return photoRepository.getHeaviestPhotos(n);
    }

    @Override
    public List<Photo> processHeaviestPhotosWithVirtualThreads(int n) {
        try {
            // Obtener las fotos más pesadas
            List<Photo> heaviestPhotos = getHeaviestPhotos(n).get();

            // Lista thread-safe para almacenar resultados
            List<Photo> processedPhotos = new CopyOnWriteArrayList<>();

            // Usar Virtual Threads para procesar cada foto
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

                // Lista para hacer seguimiento de las tareas
                List<Thread> threads = new ArrayList<>();

                // Crear un thread virtual para cada foto
                for (Photo photo : heaviestPhotos) {
                    Thread thread = Thread.ofVirtual().start(() -> {
                        // Simulación de procesamiento
                        try {
                            Thread.sleep(10); // Simular trabajo
                            // Agregar foto procesada a la lista
                            processedPhotos.add(photo);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                    threads.add(thread);
                }

                // Esperar a que todos los threads terminen
                for (Thread thread : threads) {
                    thread.join();
                }
            }

            return processedPhotos;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error processing photos", e);
        }
    }
}
