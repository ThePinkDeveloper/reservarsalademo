package com.thepinkdeveloper.bookingrooms.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Room {

	// Properties
	
	@Id
	@GeneratedValue
	private Long id;
	private String code;
	private String name;
	private Integer capacity;
	private Integer elements;
	
	@OneToMany(mappedBy="room")
	private List<Book> books;
	
	// Constructors
	
	public Room() {}

	public Room(String code, String name, Integer capacity, Integer elements) {
		super();
		this.code = code;
		this.name = name;
		this.capacity = capacity;
		this.elements = elements;
	}
	
	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getElements() {
		return elements;
	}

	public void setElements(Integer elements) {
		this.elements = elements;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
