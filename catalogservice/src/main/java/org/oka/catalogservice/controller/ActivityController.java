package org.oka.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.oka.catalogservice.domain.Activity;
import org.oka.catalogservice.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogservice")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final ModelMapper modelMapper;

    @GetMapping("/activity")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Activity> getActivities() {
        return activityService.retrieveActivities();
    }

    @GetMapping(value = "/activity/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Activity getPost(@PathVariable("id") Integer id) {
        return activityService.retrieveActivity(id);
    }

    @PostMapping("/activity")
    public Activity createActivity(@RequestBody ActivityDto activityDto) {
        return activityService.persistActivity(modelMapper.map(activityDto, Activity.class));
    }
}
