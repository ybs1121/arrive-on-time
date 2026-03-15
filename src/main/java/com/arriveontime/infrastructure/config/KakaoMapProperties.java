package com.arriveontime.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao.map")
public record KakaoMapProperties(
        String restApiKey,
        String javascriptKey
) {

    public KakaoMapProperties {
        restApiKey = normalize(restApiKey);
        javascriptKey = normalize(javascriptKey);
    }

    public boolean hasRestApiKey() {
        return !restApiKey.isBlank();
    }

    public boolean hasJavascriptKey() {
        return !javascriptKey.isBlank();
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }

        return value.trim();
    }
}
