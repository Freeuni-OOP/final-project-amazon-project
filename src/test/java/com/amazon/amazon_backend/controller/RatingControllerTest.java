package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.RatingRequest;
import com.amazon.amazon_backend.dto.RatingResponse;
import com.amazon.amazon_backend.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void addOrUpdateRating_ShouldReturnRatingResponse() throws Exception {
        // Given
        RatingRequest sampleRequest = new RatingRequest();
        sampleRequest.setUserId(1);
        sampleRequest.setProductId(100);
        sampleRequest.setStars(5);

        RatingResponse mockResponse = RatingResponse.builder()
                .ratingId(77)
                .userId(1)
                .productId(100)
                .stars(5)
                .newAverageRating(BigDecimal.valueOf(5.00))
                .totalRatingsCount(1L)
                .build();

        when(ratingService.addOrUpdateRating(any(RatingRequest.class))).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/ratings/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.ratingId").value(77))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.productId").value(100))
                .andExpect(jsonPath("$.stars").value(5))
                .andExpect(jsonPath("$.newAverageRating").value(5.0))
                .andExpect(jsonPath("$.totalRatingsCount").value(1))
                .andDo(print());
    }
}