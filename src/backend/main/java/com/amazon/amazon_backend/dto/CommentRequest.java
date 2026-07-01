package com.amazon.amazon_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest{

    @NotBlank(message = "Comment text cannot be empty or blank")
    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String commentStr;

}