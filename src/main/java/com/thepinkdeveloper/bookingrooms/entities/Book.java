package com.thepinkdeveloper.bookingrooms.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Book {

	// Properties
	
	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate date;
	private Integer fromHour;
	private Integer toHour;
	
	@ManyToOne
	@JoinColumn(name="room_id")
	private Room room;
	
	// Constructors
	
	public Book() {}

	public Book(LocalDate date, Integer fromHour, Integer toHour, Room room) {
		super();
		this.date = date;
		this.fromHour = fromHour;
		this.toHour = toHour;
		this.room = room;
	}

	// Getters and Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getFromHour() {
		return fromHour;
	}

	public void setFromHour(Integer fromHour) {
		this.fromHour = fromHour;
	}

	public Integer getToHour() {
		return toHour;
	}

	public void setToHour(Integer toHour) {
		this.toHour = toHour;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
		if (room != null) {
			room.getBooks().add(this);
		}
	}

	// toString()
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", date=" + date + ", fromHour=" + fromHour + ", toHour=" + toHour + ", room=" + room.getName()
				+ "]";
	}
	
	
	
}
