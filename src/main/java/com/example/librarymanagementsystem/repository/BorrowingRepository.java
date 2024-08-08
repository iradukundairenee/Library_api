package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.BorrowingRecord;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BorrowingRepository extends MongoRepository<BorrowingRecord, String> {
    Optional<BorrowingRecord> findByBookIdAndPatronId(String bookId, String patronId);
}