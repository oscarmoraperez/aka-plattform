package org.oka.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.oka.catalogservice.domain.Activity;
import org.oka.catalogservice.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public Activity persistActivity(Activity activity) {

        return this.activityRepository.save(activity);
    }

    public Activity retrieveActivity(int id) {

        return this.activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    public List<Activity> retrieveActivities() {

        return this.activityRepository.findAll();
    }
}
