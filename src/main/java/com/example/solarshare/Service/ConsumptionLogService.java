package com.example.solarshare.Service;

import com.example.solarshare.Entity.ConsumptionLog;
import com.example.solarshare.Entity.GenerationLog;
import com.example.solarshare.Entity.Household;
import com.example.solarshare.Repository.ConsumptionLogRepository;
import com.example.solarshare.Repository.GenerationLogRepository;
import com.example.solarshare.Repository.HouseholdRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumptionLogService {

    private final ConsumptionLogRepository repo;
    private final HouseholdRepository householdRepo;
    private final GenerationLogRepository generationRepo;

    public ConsumptionLogService(
            ConsumptionLogRepository repo,
            HouseholdRepository householdRepo,
            GenerationLogRepository generationRepo) {

        this.repo = repo;
        this.householdRepo = householdRepo;
        this.generationRepo = generationRepo;
    }

    public ConsumptionLog create(ConsumptionLog log) {

        Household household = householdRepo.findById(
                        log.getHousehold().getId())
                .orElseThrow(() ->
                        new RuntimeException("Household not found"));

        GenerationLog generation = generationRepo
                .findAll()
                .stream()
                .filter(g -> g.getDate().equals(log.getDate()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Generation not found for this date"));

        double share = household.getShareRatio() * generation.getTotalUnits();

        if (share > generation.getTotalUnits()) {
            throw new RuntimeException("Allocated share exceeds generated units");
        }

        return repo.save(log);
    }

    public List<ConsumptionLog> getAll() {
        return repo.findAll();
    }

    public ConsumptionLog getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Consumption log not found"));
    }

    public ConsumptionLog update(Long id, ConsumptionLog log) {

        ConsumptionLog existing = getById(id);

        existing.setDate(log.getDate());
        existing.setConsumedUnits(log.getConsumedUnits());
        existing.setHousehold(log.getHousehold());

        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}