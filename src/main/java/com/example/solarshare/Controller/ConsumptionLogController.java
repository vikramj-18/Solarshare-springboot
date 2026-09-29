package com.example.solarshare.Controller;

import com.example.solarshare.Entity.ConsumptionLog;
import com.example.solarshare.Service.ConsumptionLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumption")
@CrossOrigin(origins = "http://localhost:5173")
public class ConsumptionLogController {

    private final ConsumptionLogService service;

    public ConsumptionLogController(ConsumptionLogService service) {
        this.service = service;
    }

    @PostMapping
    public ConsumptionLog create(@RequestBody ConsumptionLog log) {
        return service.create(log);
    }

    @GetMapping
    public List<ConsumptionLog> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ConsumptionLog getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ConsumptionLog update(@PathVariable Long id,
                                 @RequestBody ConsumptionLog log) {
        return service.update(id, log);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Consumption deleted successfully";
    }
}