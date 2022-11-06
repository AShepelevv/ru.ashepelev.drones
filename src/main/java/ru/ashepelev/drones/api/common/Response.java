package ru.ashepelev.drones.api.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "API response container either data eight error is not null")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<D, E> {
    @Schema(description = "Response data")
    private D data;
    @Schema(description = "Response error")
    private E error;

    public static <D> Response<D, Object> success(D data) {
        return new Response<>(data, null);
    }

    public static <E> Response<Object, E> error(E error) {
        return new Response<>(null, error);
    }
}
