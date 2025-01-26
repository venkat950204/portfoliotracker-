package com.example.portfolio_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    // Define the RestTemplate bean
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(); // This creates an instance of RestTemplate
    }

    // Define global CORS configuration
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Applies to all endpoints
                .allowedOrigins("http://localhost:3001") // Allow requests from the React frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Methods to allow
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Enable cookies or credentials if needed
    }
}
