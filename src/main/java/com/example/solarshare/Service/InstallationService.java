package com.example.solarshare.Service;

import com.example.solarshare.Entity.Installation;
import com.example.solarshare.Repository.InstallationRepository;
import org.springframework.stereotype.Service;
import com.example.solarshare.Exception.ResourceNotFoundException;

import java.util.List;

@Service
public class InstallationService {

    private final InstallationRepository repo;

    public InstallationService(InstallationRepository repo) {
        this.repo = repo;
    }

    public Installation create(Installation installation) {
        return repo.save(installation);
    }

    public List<Installation> getAll() {
        return repo.findAll();
    }

    public Installation getById(Long id) {
        return repo.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Installation not found"));
    }

    public Installation update(Long id, Installation installation) {

        Installation existing = getById(id);

        existing.setCommunityName(installation.getCommunityName());
        existing.setAllocationRatio(installation.getAllocationRatio());

        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}