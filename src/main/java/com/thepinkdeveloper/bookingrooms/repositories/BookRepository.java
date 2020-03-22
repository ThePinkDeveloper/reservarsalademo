package com.thepinkdeveloper.bookingrooms.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thepinkdeveloper.bookingrooms.entities.Book;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Serializable>{

	public abstract List<Book> findByDateOrderByRoomAscFromHourAsc(LocalDate date);
	
}
