package com.example.solarshare.Service;

import com.example.solarshare.Entity.Household;
import com.example.solarshare.Repository.HouseholdRepository;
import org.springframework.stereotype.Service;
import com.example.solarshare.Exception.ResourceNotFoundException;

import java.util.List;

@Service
public class HouseholdService {

    private final HouseholdRepository repo;

    public HouseholdService(HouseholdRepository repo) {
        this.repo = repo;
    }

    public Household create(Household household) {
        return repo.save(household);
    }

    public List<Household> getAll() {
        return repo.findAll();
    }

    public Household getById(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new ResourceNotFoundException("Household not found"));
    }

    public Household update(Long id, Household household) {

        Household existing = getById(id);

        existing.setOwnerName(household.getOwnerName());
        existing.setShareRatio(household.getShareRatio());

        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}