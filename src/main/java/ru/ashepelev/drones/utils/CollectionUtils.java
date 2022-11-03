package ru.ashepelev.drones.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;

public final class CollectionUtils {
    private CollectionUtils() {}
    public static <V, U> List<U> map(Collection<V> collection, Function<V, U> mapper) {
        return collection.stream()
                .map(mapper)
                .collect(toUnmodifiableList());
    }

    public static <V, U> Set<U> mapToSet(Collection<V> collection, Function<V, U> mapper) {
        return collection.stream()
                .map(mapper)
                .collect(toUnmodifiableSet());
    }

    public static <V, U> List<U> filterAndMap(Collection<V> collection, Predicate<V> predicate, Function<V, U> mapper) {
        return collection.stream()
                .filter(predicate)
                .map(mapper)
                .collect(toUnmodifiableList());
    }

    public static <D, K, V> Map<K, V> toMap(Collection<D> collection, Function<D, K> keyMapper,
                                            Function<D, V> valueMapper) {
        return collection.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public static <K, V> Map<K, V> toKeyMap(Collection<V> collection, Function<V, K> keyMapper) {
        return toMap(collection, keyMapper, identity());
    }

    public static <V> List<V> minus(Collection<V> minuend, Collection<V> subtrahend) {
        return minuend.stream()
                .filter(not(subtrahend::contains))
                .collect(toUnmodifiableList());
    }
}
