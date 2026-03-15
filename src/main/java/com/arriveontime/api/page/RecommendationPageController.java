package com.arriveontime.api.page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.arriveontime.infrastructure.config.KakaoMapProperties;

@Controller
public class RecommendationPageController {

    private final KakaoMapProperties kakaoMapProperties;

    public RecommendationPageController(KakaoMapProperties kakaoMapProperties) {
        this.kakaoMapProperties = kakaoMapProperties;
    }

    @GetMapping({"/", "/recommendations"})
    public String recommendationPage(Model model) {
        model.addAttribute("pageTitle", "\uCD94\uCC9C \uCD9C\uBC1C \uC2DC\uAC04 \uACC4\uC0B0\uAE30");
        model.addAttribute("apiPath", "/api/recommendations");
        model.addAttribute("defaultTargetArrivalTime", defaultTargetArrivalTime());
        model.addAttribute("kakaoJavascriptKey", kakaoMapProperties.javascriptKey());
        model.addAttribute("hasKakaoJavascriptKey", kakaoMapProperties.hasJavascriptKey());

        return "recommendation-form";
    }

    private String defaultTargetArrivalTime() {
        return LocalDateTime.now()
                .plusHours(1)
                .withSecond(0)
                .withNano(0)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }
}
