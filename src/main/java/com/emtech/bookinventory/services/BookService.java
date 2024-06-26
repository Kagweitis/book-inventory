package com.emtech.bookinventory.services;

import com.emtech.bookinventory.dto.ResponseObject;
import com.emtech.bookinventory.dto.RestResponse;
import com.emtech.bookinventory.entities.BookEntity;
import com.emtech.bookinventory.repositories.BookRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BookService {

    private final BookRepo bookRepository;

    public ResponseEntity<RestResponse> getAllBooks(){
        RestResponse restResponse = new RestResponse();
        ResponseObject res = new ResponseObject();
        log.info("above try block");

        try{
            log.info("getting books");
            List<BookEntity> bookList = bookRepository.findByDeletedFalse();

            if (bookList.isEmpty()) {
                res.setMessage("No books have been found!");
                restResponse.setBody(res);
                return ResponseEntity.status(204).body(restResponse);
            }

            res.setPayload(bookList);
            restResponse.setBody(res);
            return ResponseEntity.status(HttpStatus.OK).body(restResponse);

        } catch (Exception e){
            log.error(e.getMessage());
            restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponse);
        }
    }

    public ResponseEntity<RestResponse> updateBook (Long id, BookEntity bookEntity){

        RestResponse restResponse = new RestResponse();
        ResponseObject res = new ResponseObject();

        try {
            Optional<BookEntity> oldBookData = bookRepository.findById(id);
            if (oldBookData.isPresent()) {
                BookEntity updatedData = oldBookData.get();
                updatedData.setTitle(bookEntity.getTitle());
                updatedData.setAuthor(bookEntity.getAuthor());
                BookEntity bookObj = bookRepository.save(updatedData);
                res.setMessage("successfully changed book info");
                res.setPayload(bookObj);
                restResponse.setBody(res);
                return ResponseEntity.status(HttpStatus.OK).body(restResponse);

        }

        res.setMessage("not found");
        restResponse.setBody(res);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restResponse);

    } catch (Exception e){
            log.error(e.getMessage());
            res.setMessage("an error occured");
            restResponse.setBody(res);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponse);

        }
    }


    public ResponseEntity<RestResponse> deleteBook(Long id){

        RestResponse restResponse = new RestResponse();
        ResponseObject res = new ResponseObject();

        try {
            Optional<BookEntity> bookOptional = bookRepository.findById(id);

            if (bookOptional.isPresent()) {
                log.info("book is present");
                bookOptional.get().setDeleted(true);
                bookRepository.save(bookOptional.get());
                res.setMessage("book deleted successfully");
                restResponse.setBody(res);
                return ResponseEntity.status(HttpStatus.OK).body(restResponse);
            } else {
                res.setMessage("Not found");
                restResponse.setBody(res);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restResponse);
            }
        } catch (Exception e){
            log.error(e.getMessage());
            res.setMessage("an error occured");
            restResponse.setBody(res);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponse);
        }
    }

    public ResponseEntity<RestResponse> trackStatus(Long id){
        RestResponse restResponse = new RestResponse();
        ResponseObject res = new ResponseObject();

        try {
            Optional<BookEntity> bookOptional = bookRepository.findById(id);

            if (bookOptional.isPresent()){
               String status = bookOptional.get().getStatus();
                res.setPayload(status);
                restResponse.setBody(res);
                restResponse.setStatus(HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.OK).body(restResponse);
        } catch (Exception e){
            log.error(e.getMessage());
            res.setMessage("an error occured");
            restResponse.setBody(res);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponse);
        }
    }

    public ResponseEntity<RestResponse> addBook(BookEntity bookEntity){
        RestResponse restResponse = new RestResponse();
        ResponseObject res = new ResponseObject();

        try {
            bookEntity.setStatus("pending");

            Optional<BookEntity> existingBook = bookRepository.findByIsbn(bookEntity.getIsbn());


            if (existingBook.isPresent()){
                log.info("book exists");
                res.setMessage("Book with that ISBN already exists");
                restResponse.setBody(res);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(restResponse);
            }
            bookRepository.save(bookEntity);
            res.setMessage("book added successfully");
            restResponse.setBody(res);
            restResponse.setStatus(HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(restResponse);

        } catch (Exception e){
            log.error(e.getMessage());
            res.setMessage("an error occured");
            restResponse.setBody(res);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponse);
        }
    }

    public ResponseEntity<RestResponse> updateStatus(Long id){
        RestResponse restResponse = new RestResponse();
        ResponseObject res = new ResponseObject();

        try {
            Optional<BookEntity> bookOptional = bookRepository.findById(id);

            if (bookOptional.isPresent()){
               bookOptional.get().setStatus("Verified");
               log.info("verification status " + bookOptional.get().getStatus());
               bookRepository.save(bookOptional.get());
               res.setMessage("verified successfully");
                restResponse.setBody(res);
                restResponse.setStatus(HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.OK).body(restResponse);
        } catch (Exception e){
            log.error(e.getMessage());
            res.setMessage("an error occured");
            restResponse.setBody(res);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponse);
        }
    }



}
