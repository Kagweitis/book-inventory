package com.emtech.bookinventory.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private int year;

    @Column(name = "genre")
    private String genre;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    private String status;

    @Column(name = "deleted")
    private boolean deleted;
}
