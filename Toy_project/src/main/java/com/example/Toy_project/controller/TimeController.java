package com.example.Toy_project.controller;

import com.example.Toy_project.domain.TimeForm;
import com.example.Toy_project.domain.UserGroup;
import com.example.Toy_project.service.GroupService;
import com.example.Toy_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
public class TimeController {

    private final UserService userService;


    @PostMapping("/api/PostTime")
    public String createGroup(@RequestBody TimeForm timeForm){
        int hour = timeForm.getLocalTime().getHour();
        int minute = timeForm.getLocalTime().getMinute();
        int second = timeForm.getLocalTime().getSecond();

        LocalTime localTime = LocalTime.of(hour, minute, second);

        userService.saveTime(timeForm.getId(), localTime);
        System.out.println("======= localtime : " + localTime);

        return "{ time : \"" + localTime + "\"}";
    }
}
