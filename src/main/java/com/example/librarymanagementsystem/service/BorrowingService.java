package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.BorrowingRecord;
import com.example.librarymanagementsystem.model.Patron;
import com.example.librarymanagementsystem.repository.BorrowingRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;
    private final BookService bookService;
    private final PatronService patronService;

    public BorrowingService(BorrowingRepository borrowingRepository, BookService bookService, PatronService patronService) {
        this.borrowingRepository = borrowingRepository;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    public BorrowingRecord borrowBook(String bookId, String patronId) {
        Optional<Book> book = bookService.getBookById(bookId);
        Optional<Patron> patron = patronService.getPatronById(patronId);

        if (book.isPresent() && patron.isPresent()) {
            BorrowingRecord borrowingRecord = new BorrowingRecord();
            borrowingRecord.setBook(book.get());
            borrowingRecord.setPatron(patron.get());
            borrowingRecord.setBorrowedDate(LocalDate.now());
            return borrowingRepository.save(borrowingRecord);
        } else {
            throw new IllegalArgumentException("Invalid book or patron ID");
        }
    }

    public BorrowingRecord returnBook(String bookId, String patronId) {
    Optional<BorrowingRecord> borrowingRecord = borrowingRepository.findByBookIdAndPatronId(bookId, patronId);

    if (borrowingRecord.isPresent()) {
        borrowingRecord.get().setReturnedDate(LocalDate.now());
        return borrowingRepository.save(borrowingRecord.get());
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No borrowing record found for the given book and patron");
    }
}

}
