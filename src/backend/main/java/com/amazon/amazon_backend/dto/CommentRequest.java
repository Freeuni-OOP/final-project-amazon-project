package com.amazon.amazon_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest{
    private String commentStr;
    private Integer productId;
    private Integer userId;
}