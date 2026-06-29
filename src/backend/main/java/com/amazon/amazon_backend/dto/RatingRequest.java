package com.amazon.amazon_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    @Min(value=1)
    @Max(value = 5)
    private Integer stars;
}
