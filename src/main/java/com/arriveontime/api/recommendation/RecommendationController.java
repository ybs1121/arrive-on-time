package com.arriveontime.api.recommendation;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arriveontime.application.recommendation.RecommendDepartureUseCase;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendDepartureUseCase recommendDepartureUseCase;

    public RecommendationController(RecommendDepartureUseCase recommendDepartureUseCase) {
        this.recommendDepartureUseCase = recommendDepartureUseCase;
    }

    @PostMapping
    public RecommendDepartureResponse recommend(@Valid @RequestBody RecommendDepartureRequest request) {
        return RecommendDepartureResponse.from(recommendDepartureUseCase.recommend(request.toCommand()));
    }
}
