package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.RatingRequest;
import com.amazon.amazon_backend.dto.RatingResponse;
import com.amazon.amazon_backend.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/addOrUpdate")
    public RatingResponse addOrUpdateRating(@RequestBody RatingRequest ratingRequest){
        return ratingService.addOrUpdateRating(ratingRequest);
    }
}
