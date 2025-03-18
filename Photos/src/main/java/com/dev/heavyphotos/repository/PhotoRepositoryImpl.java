package com.dev.heavyphotos.repository;

import com.dev.heavyphotos.client.JsonPlaceholderApiClient;
import com.dev.heavyphotos.model.Photo;
import com.dev.heavyphotos.client.ApiClient;
import com.dev.heavyphotos.util.PhotoComparator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementación del repositorio de fotos.
 * Utiliza el patrón Factory Method para crear instancias de dependencias.
 */
public class PhotoRepositoryImpl implements PhotoRepository {

    private final ApiClient apiClient;

    /**
     * Constructor que implementa Dependency Injection.
     *
     * @param apiClient El cliente de API a utilizar
     */
    public PhotoRepositoryImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Factory method para crear una instancia con el cliente por defecto.
     *
     * @return Una nueva instancia del repositorio
     */
    public static PhotoRepositoryImpl createDefault() {
        return new PhotoRepositoryImpl(JsonPlaceholderApiClient.getInstance());
    }

    @Override
    public CompletableFuture<List<Photo>> getAllPhotos() {
        return apiClient.getAllPhotos();
    }

    @Override
    public CompletableFuture<List<Photo>> getHeaviestPhotos(int n) {
        return getAllPhotos()
                .thenApply(photos -> photos.stream()
                        .sorted(PhotoComparator.BY_WEIGHT_DESC)
                        .limit(n)
                        .toList());
    }
}
