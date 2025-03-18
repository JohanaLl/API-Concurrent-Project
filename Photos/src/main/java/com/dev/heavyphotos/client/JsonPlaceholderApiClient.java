package com.dev.heavyphotos.client;

import com.dev.heavyphotos.model.Photo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementación del cliente API para JSONPlaceholder.
 * Utiliza HttpClient con Virtual Threads para realizar llamadas asíncronas.
 */
public class JsonPlaceholderApiClient implements ApiClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Constructor que implementa el patrón Singleton usando Holder.
     */
    private JsonPlaceholderApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Holder para implementación lazy del patrón Singleton.
     */
    private static class Holder {
        private static final JsonPlaceholderApiClient INSTANCE = new JsonPlaceholderApiClient();
    }

    /**
     * Obtiene la instancia única del cliente.
     *
     * @return La instancia del cliente
     */
    public static JsonPlaceholderApiClient getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public CompletableFuture<List<Photo>> getAllPhotos() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/photos"))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parsePhotoList);
    }

    @Override
    public CompletableFuture<Photo> getPhotoById(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/photos/" + id))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parsePhoto);
    }

    /**
     * Parsea la respuesta JSON a una lista de fotos.
     *
     * @param json El JSON a parsear
     * @return Lista de fotos
     */
    private List<Photo> parsePhotoList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing photo list", e);
        }
    }

    /**
     * Parsea la respuesta JSON a una foto.
     *
     * @param json El JSON a parsear
     * @return La foto
     */
    private Photo parsePhoto(String json) {
        try {
            return objectMapper.readValue(json, Photo.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing photo", e);
        }
    }
}
