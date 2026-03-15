package com.arriveontime.api.recommendation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.arriveontime.application.recommendation.RecommendDepartureResult;
import com.arriveontime.application.recommendation.RecommendDepartureUseCase;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendDepartureUseCase recommendDepartureUseCase;

    @Test
    void 추천_요청을_받아_결과를_반환한다() throws Exception {
        given(recommendDepartureUseCase.recommend(any()))
                .willReturn(new RecommendDepartureResult(
                        "잠실역",
                        "강남역",
                        LocalDateTime.of(2026, 3, 16, 9, 0),
                        true,
                        0,
                        LocalDateTime.of(2026, 3, 16, 9, 0),
                        25,
                        10,
                        LocalDateTime.of(2026, 3, 16, 8, 25)
                ));

        String requestBody = """
                {
                  "origin": "잠실역",
                  "destination": "강남역",
                  "targetArrivalTime": "2026-03-16T09:00:00",
                  "strict": true,
                  "allowedDelayMinutes": 0
                }
                """;

        mockMvc.perform(post("/api/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin").value("잠실역"))
                .andExpect(jsonPath("$.destination").value("강남역"))
                .andExpect(jsonPath("$.expectedTravelMinutes").value(25))
                .andExpect(jsonPath("$.bufferMinutes").value(10))
                .andExpect(jsonPath("$.recommendedDepartureTime").value("2026-03-16T08:25:00"));
    }

    @Test
    void 필수값이_없으면_검증_오류를_반환한다() throws Exception {
        String requestBody = """
                {
                  "origin": "",
                  "destination": "강남역",
                  "strict": true,
                  "allowedDelayMinutes": 0
                }
                """;

        mockMvc.perform(post("/api/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.validationErrors.origin").value("출발지는 비어 있을 수 없습니다."))
                .andExpect(jsonPath("$.validationErrors.targetArrivalTime").value("목표 도착 시각은 비어 있을 수 없습니다."));
    }

    @Test
    void 잘못된_요청_본문이면_본문_오류를_반환한다() throws Exception {
        String requestBody = """
                {
                  "origin": "잠실역",
                  "destination": "강남역",
                  "targetArrivalTime": "잘못된값",
                  "strict": true,
                  "allowedDelayMinutes": 0
                }
                """;

        mockMvc.perform(post("/api/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST_BODY"))
                .andExpect(jsonPath("$.message").value("요청 본문 형식이 올바르지 않습니다."));
    }
}
