package com.capstone.usa.jobpost.service;

import com.capstone.usa.jobpost.dto.CreateJobDto;
import com.capstone.usa.jobpost.model.Job;
import com.capstone.usa.jobpost.repository.JobRepository;
import com.capstone.usa.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JobService {
    private JobRepository jobRepository;

    public List<Job> getJob() {
        return jobRepository.findAll();
    }

    public void createJob(User user, CreateJobDto dto) {
        LocalDateTime now = LocalDateTime.now();
        Job job = new Job(
                0,
                dto.getTitle(),
                dto.getContent(),
                user,
                now,
                now
        );

        jobRepository.save(job);
    }

    public Job findJobById(int jobId) {
        Optional<Job> jobOptional = jobRepository.findById(String.valueOf(jobId));
        return jobOptional.orElse(null);
    }
}
