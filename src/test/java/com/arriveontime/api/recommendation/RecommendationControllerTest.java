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
import com.arriveontime.application.route.RoutePoint;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendDepartureUseCase recommendDepartureUseCase;

    @Test
    void returnsRecommendationResponse() throws Exception {
        given(recommendDepartureUseCase.recommend(any()))
                .willReturn(new RecommendDepartureResult(
                        new RoutePoint("Jamsil Station", 37.5132612, 127.1001336),
                        new RoutePoint("Gangnam Station", 37.497175, 127.027926),
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
                  "origin": {
                    "name": "Jamsil Station",
                    "latitude": 37.5132612,
                    "longitude": 127.1001336
                  },
                  "destination": {
                    "name": "Gangnam Station",
                    "latitude": 37.497175,
                    "longitude": 127.027926
                  },
                  "targetArrivalTime": "2026-03-16T09:00:00",
                  "strict": true,
                  "allowedDelayMinutes": 0
                }
                """;

        mockMvc.perform(post("/api/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin.name").value("Jamsil Station"))
                .andExpect(jsonPath("$.destination.name").value("Gangnam Station"))
                .andExpect(jsonPath("$.origin.latitude").value(37.5132612))
                .andExpect(jsonPath("$.destination.longitude").value(127.027926))
                .andExpect(jsonPath("$.expectedTravelMinutes").value(25))
                .andExpect(jsonPath("$.bufferMinutes").value(10))
                .andExpect(jsonPath("$.recommendedDepartureTime").value("2026-03-16T08:25:00"));
    }

    @Test
    void returnsValidationErrorWhenRequiredFieldsMissing() throws Exception {
        String requestBody = """
                {
                  "origin": {
                    "name": "",
                    "latitude": 37.5132612,
                    "longitude": 127.1001336
                  },
                  "destination": {
                    "name": "Gangnam Station",
                    "latitude": 37.497175,
                    "longitude": 127.027926
                  },
                  "strict": true,
                  "allowedDelayMinutes": 0
                }
                """;

        mockMvc.perform(post("/api/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.validationErrors['origin.name']")
                        .value("\uC7A5\uC18C \uC774\uB984\uC740 \uBE44\uC5B4 \uC788\uC744 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4."))
                .andExpect(jsonPath("$.validationErrors.targetArrivalTime")
                        .value("\uBAA9\uD45C \uB3C4\uCC29 \uC2DC\uAC01\uC740 \uBE44\uC5B4 \uC788\uC744 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4."));
    }

    @Test
    void returnsInvalidBodyErrorWhenDateTimeIsMalformed() throws Exception {
        String requestBody = """
                {
                  "origin": {
                    "name": "Jamsil Station",
                    "latitude": 37.5132612,
                    "longitude": 127.1001336
                  },
                  "destination": {
                    "name": "Gangnam Station",
                    "latitude": 37.497175,
                    "longitude": 127.027926
                  },
                  "targetArrivalTime": "wrong-value",
                  "strict": true,
                  "allowedDelayMinutes": 0
                }
                """;

        mockMvc.perform(post("/api/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST_BODY"))
                .andExpect(jsonPath("$.message")
                        .value("\uC694\uCCAD \uBCF8\uBB38 \uD615\uC2DD\uC774 \uC62C\uBC14\uB974\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4."));
    }
}
