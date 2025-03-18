package com.dev.heavyphotos.model;

import java.util.Objects;

/**
 * Clase que representa una foto de la API JSONPlaceholder.
 * Implementa el patrón DTO (Data Transfer Object).
 */
public record Photo(
        int albumId,
        int id,
        String title,
        String url,
        String thumbnailUrl
) {

    /**
     * Calcula el "peso" de la foto basado en el ID y la longitud del título.
     * Este es un ejemplo de método utilizado para ordenar las fotos.
     *
     * @return El peso calculado de la foto
     */
    public int getWeight() {
        return id + title.length() * 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id == photo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Photo{" +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", weight='" +  getWeight()  + '\'' +
                '}';
    }
}
