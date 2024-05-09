package org.oka.catalogservice;

import org.oka.catalogservice.domain.Activity;
import org.oka.catalogservice.repository.ActivityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;

@SpringBootApplication
public class CatalogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ActivityRepository activityRepository) {
        return args ->
        {
            activityRepository.save(Activity.builder()
                    .name("Stoos Hike")
                    .description("Hike around the Stoos area")
                    .duration("4 hours")
                    .recommendedAges("8 to 80")
                    .price(BigInteger.valueOf(12))
                    .currency("CHF")
                    .build());
            activityRepository.save(Activity.builder()
                    .name("Maenlichen to Wengen Hike")
                    .description("Demanding hike around the Grindelwand area")
                    .duration("6 hours")
                    .recommendedAges("12 to 80")
                    .price(BigInteger.valueOf(14))
                    .currency("CHF")
                    .build());
        };
    }
}
