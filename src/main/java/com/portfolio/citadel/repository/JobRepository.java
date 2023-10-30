package com.portfolio.citadel.repository;

import com.portfolio.citadel.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
