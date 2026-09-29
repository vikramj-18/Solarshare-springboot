package com.example.solarshare.Repository;

import com.example.solarshare.Entity.GenerationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationLogRepository extends JpaRepository<GenerationLog, Long> {
}