package com.amazon.amazon_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReviewRequest {
    private String comment_STR;
    private Integer rating;
}
