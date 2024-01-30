package com.codingapi.gemini;

import com.codingapi.gemini.client.GeminiClient;
import com.codingapi.gemini.properties.GeminiProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "gemini")
    public GeminiProperties geminiProperties(){
        return new GeminiProperties();
    }

    @Bean
    public GeminiClient geminiClient(GeminiProperties geminiProperties){
        return new GeminiClient(geminiProperties.getApiKey(), geminiProperties.getProxyHost(), geminiProperties.getProxyPort());
    }


}
