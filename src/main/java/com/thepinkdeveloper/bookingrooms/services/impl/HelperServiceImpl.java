package com.thepinkdeveloper.bookingrooms.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.thepinkdeveloper.bookingrooms.entities.Book;
import com.thepinkdeveloper.bookingrooms.entities.Room;
import com.thepinkdeveloper.bookingrooms.repositories.BookRepository;
import com.thepinkdeveloper.bookingrooms.repositories.RoomRepository;
import com.thepinkdeveloper.bookingrooms.services.HelperService;
import com.thepinkdeveloper.bookingrooms.staticdata.Elements;
import com.thepinkdeveloper.bookingrooms.staticdata.RoomNames;

@Service("helperServiceImpl")
public class HelperServiceImpl implements HelperService, Elements {
	
	private final Integer MIN_CAPACITY = 2;
	private final Integer MAX_CAPACITY = 20;
	private final Integer DAYS_BOOKED = 15;
	private final Integer MAX_VALUE_TO_BOOK = 12;
	private final Integer NUMBER_OF_RANGES = 60; 
	
	@Autowired
	@Qualifier("roomRepository")
	private RoomRepository roomRepository;

	@Autowired
	@Qualifier("bookRepository")
	private BookRepository bookRepository;
	
	@Override
	public void createInitialRooms() {
		List<Room> rooms = new ArrayList<>();
		List<String> roomNames = RoomNames.getRoomsNames();
		roomNames.stream().forEach( name -> {
			String roomName = name;
			String code = name.replace(" ", "-").substring(0, 4).toUpperCase();
			Integer capacity = randomNumberInRange(MIN_CAPACITY, MAX_CAPACITY);
			Integer elements = generateElements();
			rooms.add(new Room(code, roomName, capacity, elements));
		});
		roomRepository.saveAll(rooms);
	}

	@Override
	public void createInitialBooks() {
		List<Room> rooms = roomRepository.findAll();
		List<Book> books = new ArrayList<>();
		rooms.stream().forEach( room -> {
			int number = 0;
			for (int i = 0; i < DAYS_BOOKED; i++) {
				LocalDate date = LocalDate.now().plusDays(i);
				while (number <= NUMBER_OF_RANGES - 1) {
					int fromHour = number + randomNumberInRange(0, MAX_VALUE_TO_BOOK);
					number = fromHour;
					int toHour = number + randomNumberInRange(0, MAX_VALUE_TO_BOOK);
					number = toHour;
					if (number <= NUMBER_OF_RANGES - 1) {
						books.add(new Book(date, fromHour, toHour, room));
					}
				}
				number = 0;
			}
		});
		bookRepository.saveAll(books);
	}
	
	private int randomNumberInRange(int min, int max) {
		Random rn = new Random();
		return rn.nextInt((max - min) + 1) + min;
	}
	
	private Integer generateElements() {
		Random rn = new Random();
		Integer elements = 1;
		for (int n = 0; n < MAX_ELEMENTS; n++) {
			if( rn.nextInt(2) == 1) {
				elements *= listElements.get(n);
			}
		}	
		return elements;
	}

}