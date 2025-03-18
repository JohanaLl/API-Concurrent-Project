package com.dev.heavyphotos.util;

import com.dev.heavyphotos.model.Photo;

import java.util.Comparator;

/**
 * Implementación del patrón Strategy para proporcionar diferentes estrategias de comparación.
 * Permite cambiar el algoritmo de comparación utilizado para ordenar las fotos.
 */
public class PhotoComparator {

    /**
     * Comparador por peso de la foto (descendente)
     */
    public static final Comparator<Photo> BY_WEIGHT_DESC =
            (p1, p2) -> Integer.compare(p2.getWeight(), p1.getWeight());

    /**
     * Comparador por ID (ascendente)
     */
    public static final Comparator<Photo> BY_ID_ASC =
            Comparator.comparingInt(Photo::id);

    /**
     * Comparador por título (alfabético)
     */
    public static final Comparator<Photo> BY_TITLE_ASC =
            Comparator.comparing(Photo::title);

    /**
     * Crea un comparador personalizado basado en una función dada.
     *
     * @param keyExtractor La función para extraer la clave de comparación
     * @param <T> El tipo de la clave
     * @return Un nuevo comparador
     */
    public static <T extends Comparable<T>> Comparator<Photo> createComparator(
            java.util.function.Function<Photo, T> keyExtractor) {
        return Comparator.comparing(keyExtractor);
    }
}
