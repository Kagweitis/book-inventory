package com.emtech.bookinventory.repositories;

import com.emtech.bookinventory.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByTitle(String title);


}
