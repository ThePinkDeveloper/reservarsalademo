package com.thepinkdeveloper.bookingrooms.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.thepinkdeveloper.bookingrooms.converters.RoomConverter;
import com.thepinkdeveloper.bookingrooms.entities.Book;
import com.thepinkdeveloper.bookingrooms.entities.Room;
import com.thepinkdeveloper.bookingrooms.models.AdvancedRoomModel;
import com.thepinkdeveloper.bookingrooms.repositories.BookRepository;
import com.thepinkdeveloper.bookingrooms.repositories.RoomRepository;
import com.thepinkdeveloper.bookingrooms.services.BookService;

@Service("bookServiceImpl")
public class BookServiceImpl implements BookService{
	
	private final static Integer MAX_OCCUPANCY= 59; // de 7:00 a 22:00 
	
	@Autowired
	@Qualifier("roomConverter")
	private RoomConverter roomConverter;
	
	@Autowired
	@Qualifier("bookRepository")
	private BookRepository bookRepository;
	
	@Autowired
	@Qualifier("roomRepository")
	private RoomRepository roomRepository;
	
	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Map<Boolean, AdvancedRoomModel> bookARoom(LocalDate date, Integer users, Integer fromHour, Integer toHour, Integer elements) {
		Map<Boolean, AdvancedRoomModel> hadSuccess = new HashMap<>();
		hadSuccess.put(false, new AdvancedRoomModel());
		try {
			List<AdvancedRoomModel> bsmList = new ArrayList<>();
			Set<Room> overlappingRooms = new HashSet<>();
			List<Book> books = bookRepository.findByDateOrderByRoomAscFromHourAsc(date);
			if (!books.isEmpty()) {
				books.stream()
					.forEach( book -> {
						if (isOverlappingRange(book.getFromHour(), book.getToHour(),
											   fromHour, toHour)) {
							overlappingRooms.add(book.getRoom());	
						}
					});
				
				books.stream()
					.filter( book -> book.getRoom().getCapacity() >= users)
					.filter( book -> book.getRoom().getElements() % elements == 0)		
					.filter( book -> !overlappingRooms.contains(book.getRoom()))
				    .forEach(book -> {
				    	List<String> bsmRoomCodes = new ArrayList<>();
				    	bsmList.forEach( bsm -> bsmRoomCodes.add(bsm.getCode()));
				    	if (bsmRoomCodes.contains(book.getRoom().getCode())) {
				    		bsmList.stream().filter( bsm -> 
				    			bsm.getCode().equals(book.getRoom().getCode()))
				    		.forEach( bsm -> {
				    			bsm = roomConverter.entity2model(book.getRoom(), 
				    					bsm.getFreeOccupancy() - book.getToHour() + book.getFromHour());
				    		});
				    	} else {
					    		AdvancedRoomModel bsm = new AdvancedRoomModel();
					    		bsm = roomConverter.entity2model(book.getRoom(), 
					    				MAX_OCCUPANCY - book.getToHour() + book.getFromHour());
					    		bsmList.add(bsm);
				    	}
				    });
				
				if (!bsmList.isEmpty()) {
					bsmList.sort(Comparator.comparing(AdvancedRoomModel::getCapacity)
							.thenComparing(AdvancedRoomModel::getElements)
							.thenComparing(AdvancedRoomModel::getFreeOccupancy));
					AdvancedRoomModel result = bsmList.get(0);
					Book bookResult = new Book(date, fromHour, toHour, roomRepository.findByCode(result.getCode()));
					hadSuccess.put(true, result);
					bookRepository.save(bookResult);
				}
				
			}

		} catch(Exception e) {
			hadSuccess.put(false, null);
			e.printStackTrace();
		}
		return hadSuccess;
	}

	@Override
	public boolean deleteABook(Book book) {
		boolean hadSuccess = false;
		try {
			bookRepository.delete(book);
			hadSuccess = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return hadSuccess;
	}

	@Override
	public boolean updateABook(Book book) {
		boolean hadSuccess = false;
		try {
			Book bookToUpdate = bookRepository.getOne(book.getId());
			if (bookToUpdate != null) {
				bookRepository.save(book);
				hadSuccess = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return hadSuccess;
	}
	
	private boolean isOverlappingRange(Integer fromHourExistingBook, 
									   Integer toHourExistingBook, 
									   Integer fromHourWantedBook, 
									   Integer toHourWantedBook) {
		for (int i = fromHourExistingBook; i <= toHourExistingBook; i++) {
			if (i >= fromHourWantedBook && i <= toHourWantedBook) {
				return true;
			}
		}
		return false;
	}

}
