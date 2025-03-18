package com.dev.heavyphotos.repository;

import com.dev.heavyphotos.model.Photo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interfaz que define las operaciones del repositorio de fotos.
 * Implementa el patrón Repository para abstraer el acceso a datos.
 */
public interface PhotoRepository {

    /**
     * Obtiene todas las fotos de forma asíncrona.
     *
     * @return CompletableFuture con la lista de fotos
     */
    CompletableFuture<List<Photo>> getAllPhotos();

    /**
     * Obtiene las N fotos más pesadas según el criterio de peso.
     *
     * @param n El número de fotos a obtener
     * @return CompletableFuture con la lista de las N fotos más pesadas
     */
    CompletableFuture<List<Photo>> getHeaviestPhotos(int n);
}
