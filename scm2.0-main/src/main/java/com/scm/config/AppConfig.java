package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {

    // Commented out the @Value injections to disable Cloudinary temporarily
    // @Value("${cloudinary.cloud.name}")
    // private String cloudName;

    // @Value("${cloudinary.api.key}")
    // private String apiKey;

    // @Value("${cloudinary.api.secret}")
    // private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        // Provide dummy values to avoid placeholder errors
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dummy",
                "api_key", "dummy",
                "api_secret", "dummy")
        );
    }
}
