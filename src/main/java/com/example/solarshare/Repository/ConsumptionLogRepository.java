package com.example.solarshare.Repository;

import com.example.solarshare.Entity.ConsumptionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionLogRepository extends JpaRepository<ConsumptionLog, Long> {
}