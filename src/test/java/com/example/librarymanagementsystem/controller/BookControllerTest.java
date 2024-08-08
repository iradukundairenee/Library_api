package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void testGetAllBooks() throws Exception {
        // Arrange
        Book book1 = new Book();
        book1.setId("123");
        book1.setTitle("Book 1");
        Book book2 = new Book();
        book2.setId("456");
        book2.setTitle("Book 2");
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    void testGetBookById() throws Exception {
        // Arrange
        String bookId = "123";
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Book 1");
        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"));
    }

    @Test
    void testCreateBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setTitle("New Book");
        book.setAuthor("Author");
        book.setPublicationYear(2023);
        book.setIsbn("978-1-2345-6789-0");

        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publicationYear").value(2023))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0"));
    }

    // Add more tests for update and delete operations
}
