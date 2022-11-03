package ru.ashepelev.drones.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<D, E> {
    private D data;
    private E error;

    public static <D> Response<D, ?> success(D data) {
        return new Response<>(data, null);
    }

    public static <E> Response<?, E> error(E error) {
        return new Response<>(null, error);
    }
}
