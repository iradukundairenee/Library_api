package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.BorrowingRecord;
import com.example.librarymanagementsystem.service.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingController {
    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable String bookId, @PathVariable String patronId) {
        BorrowingRecord borrowingRecord = borrowingService.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingRecord);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable String bookId, @PathVariable String patronId) {
        BorrowingRecord borrowingRecord = borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecord);
    }
}
