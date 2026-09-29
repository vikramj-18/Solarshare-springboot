package com.example.solarshare.Controller;

import com.example.solarshare.Entity.GenerationLog;
import com.example.solarshare.Service.GenerationLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generation")
@CrossOrigin(origins = "http://localhost:5173")
public class GenerationLogController {

    private final GenerationLogService service;

    public GenerationLogController(GenerationLogService service) {
        this.service = service;
    }

    @PostMapping
    public GenerationLog create(@RequestBody GenerationLog log) {
        return service.create(log);
    }

    @GetMapping
    public List<GenerationLog> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GenerationLog getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public GenerationLog update(@PathVariable Long id,
                                @RequestBody GenerationLog log) {
        return service.update(id, log);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Generation deleted successfully";
    }
}