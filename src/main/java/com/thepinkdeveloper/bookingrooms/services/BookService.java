package com.thepinkdeveloper.bookingrooms.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.thepinkdeveloper.bookingrooms.entities.Book;
import com.thepinkdeveloper.bookingrooms.models.AdvancedRoomModel;

public interface BookService {
	
	public abstract List<Book> getAllBooks();
	
	public abstract Map<Boolean, AdvancedRoomModel> bookARoom(LocalDate date, Integer user, Integer fromDate, Integer toDate, Integer elements);
	
	public abstract boolean deleteABook(Book book);
	
	public abstract boolean updateABook(Book book);

}
