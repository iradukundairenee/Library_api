package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
    // No need for a custom update method, use the save() method instead
}