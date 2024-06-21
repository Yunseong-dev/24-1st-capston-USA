package com.capstone.usa.job.service;

import com.capstone.usa.job.dto.CreateJobDto;
import com.capstone.usa.job.model.Job;
import com.capstone.usa.job.repository.JobRepository;
import com.capstone.usa.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class JobService {
    private JobRepository jobRepository;

    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    public void createJob(User user, CreateJobDto dto) {
        LocalDateTime now = LocalDateTime.now();
        Job job = new Job(
                null,
                dto.getTitle(),
                dto.getContent(),
                user,
                now,
                now
        );

        jobRepository.save(job);
    }

    public Job getJob(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));
    }
}
