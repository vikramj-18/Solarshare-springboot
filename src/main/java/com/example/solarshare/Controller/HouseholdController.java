package com.example.solarshare.Controller;

import com.example.solarshare.Entity.Household;
import com.example.solarshare.Service.HouseholdService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/households")
@CrossOrigin(origins = "http://localhost:5173")
public class HouseholdController {

    private final HouseholdService service;

    public HouseholdController(HouseholdService service) {
        this.service = service;
    }

    @PostMapping
    public Household create(@RequestBody Household household) {
        return service.create(household);
    }

    @GetMapping
    public List<Household> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Household getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Household update(@PathVariable Long id,
                            @RequestBody Household household) {
        return service.update(id, household);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Household deleted successfully";
    }
}