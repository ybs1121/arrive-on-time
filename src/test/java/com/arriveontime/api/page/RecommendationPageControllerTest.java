package com.arriveontime.api.page;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.arriveontime.infrastructure.config.KakaoMapProperties;

@WebMvcTest(RecommendationPageController.class)
@Import(RecommendationPageControllerTest.TestConfig.class)
class RecommendationPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsRecommendationPage() throws Exception {
        mockMvc.perform(get("/recommendations"))
                .andExpect(status().isOk())
                .andExpect(view().name("recommendation-form"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("apiPath"))
                .andExpect(model().attributeExists("kakaoJavascriptKey"))
                .andExpect(model().attribute("hasKakaoJavascriptKey", true));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        KakaoMapProperties kakaoMapProperties() {
            return new KakaoMapProperties("", "test-javascript-key");
        }
    }
}
