package com.example.solarshare.Controller;

import com.example.solarshare.Entity.Installation;
import com.example.solarshare.Service.InstallationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/installations")
@CrossOrigin(origins = "http://localhost:5173")
public class InstallationController {

    private final InstallationService service;

    public InstallationController(InstallationService service) {
        this.service = service;
    }

    @PostMapping
    public Installation create(@RequestBody Installation installation) {
        return service.create(installation);
    }

    @GetMapping
    public List<Installation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Installation getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Installation update(@PathVariable Long id,
                               @RequestBody Installation installation) {
        return service.update(id, installation);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Installation deleted successfully";
    }
}