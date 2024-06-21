package com.capstone.usa.job.controller;

import com.capstone.usa.job.dto.CreateJobDto;
import com.capstone.usa.job.model.Job;
import com.capstone.usa.job.service.JobService;
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
        return jobService.getJobs();
    }

    @GetMapping("/{id}")
    public Job getJob(
            @PathVariable Long id
    ) {
        return jobService.getJob(id);
    }

    @PostMapping
    public void createJob(
            @AuthenticationPrincipal User user,
            @RequestBody CreateJobDto dto
    ) {
        jobService.createJob(user, dto);
    }
}
