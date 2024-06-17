package com.capstone.usa.jobpost.controller;

import com.capstone.usa.jobpost.dto.CreateJobDto;
import com.capstone.usa.jobpost.model.Job;
import com.capstone.usa.jobpost.service.JobService;
import com.capstone.usa.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/job")
@AllArgsConstructor
public class JobController {
    private JobService jobService;

    @GetMapping
    public List<Job> getJob() {
        return jobService.getJob();
    }

    @PostMapping("/create")
    public void createJob(
            @AuthenticationPrincipal User user,
            @RequestBody CreateJobDto dto
    ) {
        jobService.createJob(user, dto);
    }
}
