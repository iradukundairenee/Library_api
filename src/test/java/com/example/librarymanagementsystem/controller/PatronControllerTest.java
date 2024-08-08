package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.Patron;
import com.example.librarymanagementsystem.service.PatronService;
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

@WebMvcTest(PatronController.class)
class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatronService patronService;

    @Test
    void testGetAllPatrons() throws Exception {
        // Arrange
        Patron patron1 = new Patron();
        patron1.setId("123");
        patron1.setName("John Doe");
        Patron patron2 = new Patron();
        patron2.setId("456");
        patron2.setName("Jane Smith");
        when(patronService.getAllPatrons()).thenReturn(Arrays.asList(patron1, patron2));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    void testGetPatronById() throws Exception {
        // Arrange
        String patronId = "123";
        Patron patron = new Patron();
        patron.setId(patronId);
        patron.setName("John Doe");
        when(patronService.getPatronById(patronId)).thenReturn(Optional.of(patron));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/{id}", patronId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(patronId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testCreatePatron() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setName("New Patron");
        patron.setEmail("new.patron@example.com");
        patron.setPhone("1234567890");

        when(patronService.savePatron(any(Patron.class))).thenReturn(patron);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Patron"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("new.patron@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("1234567890"));
    }

    // Add more tests for update and delete operations
}
