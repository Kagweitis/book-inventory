package com.emtech.bookinventory.controllers;

import com.emtech.bookinventory.dto.RestResponse;
import com.emtech.bookinventory.entities.BookEntity;
import com.emtech.bookinventory.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/books")
@AllArgsConstructor
public class BooksController {

    private final BookService bookService;

//    @PreAuthorize("hasRole('USER')")
    @GetMapping("get-books")
    public ResponseEntity<RestResponse> getAllBooks(){
        return bookService.getAllBooks();
    }

//    @PreAuthorize("hasRole('USER')")
    @PutMapping("update/{id}")
    public ResponseEntity<RestResponse> updateBook(@PathVariable Long id, @RequestBody BookEntity bookEntity){
        return bookService.updateBook(id, bookEntity);
    }

//    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<RestResponse> deleteBook(@PathVariable Long id){
        return bookService.deleteBook(id);
    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping("status/{id}")
    public ResponseEntity<RestResponse> trackStatus(@PathVariable Long id){
        return bookService.trackStatus(id);
    }


//    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add-book")
    public ResponseEntity<RestResponse> addBook(@RequestBody BookEntity bookEntity){
        return bookService.addBook(bookEntity);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("status-update/{id}")
    public ResponseEntity<RestResponse> updateStatus(@PathVariable Long id){
        return bookService.updateStatus(id);
    }
}
