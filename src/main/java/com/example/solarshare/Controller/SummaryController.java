package com.example.solarshare.Controller;

import com.example.solarshare.Entity.ConsumptionLog;
import com.example.solarshare.Entity.GenerationLog;
import com.example.solarshare.Entity.Household;
import com.example.solarshare.Repository.ConsumptionLogRepository;
import com.example.solarshare.Repository.GenerationLogRepository;
import com.example.solarshare.Repository.HouseholdRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/summary")
@CrossOrigin(origins = "http://localhost:5173")
public class SummaryController {

    private final HouseholdRepository householdRepo;
    private final GenerationLogRepository generationRepo;
    private final ConsumptionLogRepository consumptionRepo;

    public SummaryController(HouseholdRepository householdRepo,
                             GenerationLogRepository generationRepo,
                             ConsumptionLogRepository consumptionRepo) {
        this.householdRepo = householdRepo;
        this.generationRepo = generationRepo;
        this.consumptionRepo = consumptionRepo;
    }

    @GetMapping("/{householdId}")
    public String summary(@PathVariable Long householdId) {

        Household household = householdRepo.findById(householdId)
                .orElseThrow(() -> new RuntimeException("Household not found"));

        double totalGeneration = generationRepo.findAll()
                .stream()
                .mapToDouble(GenerationLog::getTotalUnits)
                .sum();

        double share = totalGeneration * household.getShareRatio();

        double consumption = consumptionRepo.findAll()
                .stream()
                .filter(c -> c.getHousehold().getId().equals(householdId))
                .mapToDouble(ConsumptionLog::getConsumedUnits)
                .sum();

        double export = Math.max(share - consumption, 0);

        return """
                Household: %s

                Total Generation Share: %.2f units

                Consumption: %.2f units

                Exported: %.2f units
                """.formatted(
                household.getOwnerName(),
                share,
                consumption,
                export);
    }
}