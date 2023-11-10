package com.emtech.bookinventory.controllers;

import com.emtech.bookinventory.dto.RestResponse;
import com.emtech.bookinventory.entities.BookEntity;
import com.emtech.bookinventory.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BooksController {

    private final BookService bookService;

    @GetMapping("get-books")
    public ResponseEntity<RestResponse> getAllBooks(){
        return bookService.getAllBooks();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<RestResponse> updateBook(@PathVariable Long id, @RequestBody BookEntity bookEntity){
        return bookService.updateBook(id, bookEntity);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<RestResponse> deleteBook(@PathVariable Long id){
        return bookService.deleteBook(id);
    }

    @GetMapping("status/{id}")
    public ResponseEntity<RestResponse> trackStatus(@PathVariable Long id){
        return bookService.trackStatus(id);
    }


    @PostMapping("/add-book")
    public ResponseEntity<RestResponse> addBook(@RequestBody BookEntity bookEntity){
        return bookService.addBook(bookEntity);
    }
}
