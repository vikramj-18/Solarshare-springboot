package com.example.solarshare.Service;

import com.example.solarshare.Entity.GenerationLog;
import com.example.solarshare.Repository.GenerationLogRepository;
import org.springframework.stereotype.Service;
import com.example.solarshare.Exception.ResourceNotFoundException;

import java.util.List;

@Service
public class GenerationLogService {

    private final GenerationLogRepository repo;

    public GenerationLogService(GenerationLogRepository repo) {
        this.repo = repo;
    }

    public GenerationLog create(GenerationLog log) {
        return repo.save(log);
    }

    public List<GenerationLog> getAll() {
        return repo.findAll();
    }

    public GenerationLog getById(Long id) {
        return repo.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Generation log not found"));
    }

    public GenerationLog update(Long id, GenerationLog log) {

        GenerationLog existing = getById(id);

        existing.setDate(log.getDate());
        existing.setTotalUnits(log.getTotalUnits());

        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}