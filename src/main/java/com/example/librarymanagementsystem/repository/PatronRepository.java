package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Patron;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatronRepository extends MongoRepository<Patron, String> {
}