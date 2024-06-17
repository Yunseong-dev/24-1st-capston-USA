package com.capstone.usa.jobpost.repository;

import com.capstone.usa.jobpost.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {
}
