package com.example.demo.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AirtableConfig {
    @Value("${airtable.apiBase}")
    private String apiBase;

    @Value("${airtable.token}")
    private String token;

    @Bean
    public WebClient airtableWebClient(@Value("${airtable.apiBase}") String apiBase,
                                       @Value("${airtable.token}") String token) {
        return WebClient.builder()
                .baseUrl(apiBase)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }
}
