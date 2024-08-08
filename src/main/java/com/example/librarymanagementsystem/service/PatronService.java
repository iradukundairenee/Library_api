package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Patron;
import com.example.librarymanagementsystem.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(String id) {
        return patronRepository.findById(id);
    }

    public Patron savePatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public void updatePatron(String id, Patron patron) {
        patronRepository.save(patron);
    }

    public void deletePatron(String id) {
        patronRepository.deleteById(id);
    }
}