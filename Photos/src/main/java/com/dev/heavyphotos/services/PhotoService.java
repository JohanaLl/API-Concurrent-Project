package com.dev.heavyphotos.services;

import com.dev.heavyphotos.model.Photo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interfaz que define las operaciones del servicio de fotos.
 * Implementa el patrón Service para encapsular la lógica de negocio.
 */
public interface PhotoService {
    /**
     * Obtiene las N fotos más pesadas.
     *
     * @param n El número de fotos a obtener
     * @return CompletableFuture con la lista de las N fotos más pesadas
     */
    CompletableFuture<List<Photo>> getHeaviestPhotos(int n);

    /**
     * Procesa las N fotos más pesadas usando Virtual Threads.
     * Cada foto se procesa en su propio thread virtual.
     *
     * @param n El número de fotos a procesar
     * @return Lista de fotos procesadas
     */
    List<Photo> processHeaviestPhotosWithVirtualThreads(int n);
}
