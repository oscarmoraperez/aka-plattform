package org.oka.aka.orderservice.configuration;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderServiceConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        // TODO: URL should be passed as a variable p.e. via application.properties
        return new RestTemplateBuilder().rootUri("http://localhost:8081/").build();
    }
}
