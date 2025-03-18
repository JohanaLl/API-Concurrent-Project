package com.dev.heavyphotos.client;

import com.dev.heavyphotos.model.Photo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interfaz que define las operaciones del cliente API.
 * Implementa el patrón Adapter, permitiendo intercambiar diferentes implementaciones de API.
 */
public interface ApiClient {

    /**
     * Obtiene todas las fotos de forma asíncrona.
     *
     * @return CompletableFuture con la lista de fotos
     */
    CompletableFuture<List<Photo>> getAllPhotos();

    /**
     * Obtiene una foto específica por su ID de forma asíncrona.
     *
     * @param id El ID de la foto
     * @return CompletableFuture con la foto
     */
    CompletableFuture<Photo> getPhotoById(int id);

}
