package com.capstone.usa.job.repository;

import com.capstone.usa.job.model.Job;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {
    @NotNull
    List<Job> findAll();

    Optional<Job> findById(Long id);
}
