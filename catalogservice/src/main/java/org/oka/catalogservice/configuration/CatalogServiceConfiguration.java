package org.oka.catalogservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogServiceConfiguration {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
