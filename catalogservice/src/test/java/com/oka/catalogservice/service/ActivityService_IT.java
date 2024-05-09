package com.oka.catalogservice.service;

import org.junit.jupiter.api.Test;
import org.oka.catalogservice.domain.Activity;
import org.oka.catalogservice.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@Testcontainers
public class ActivityService_IT {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> DB = new PostgreSQLContainer<>("postgres:16-alpine");
    @Autowired
    ActivityService activityService;

    @Test
    void shouldPersistActivity() {
        // Given
        Activity activity = Activity.builder().build();

        // When
        Activity persistedActivity = activityService.persistActivity(activity);

        // Then
        assertThat(persistedActivity.getId()).isNotNull();
    }
}
