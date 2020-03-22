package com.thepinkdeveloper.bookingrooms.models;

import java.time.LocalDate;

public class RoomModel {
	
	// Properties
	
	private String code;
	private String name;
	private Integer capacity;
	private Integer elements;
	private Integer freeOccupancy;
	
	// Constructors
	
	public RoomModel() {}

	public RoomModel(String code, String name, Integer capacity, Integer elements, LocalDate date, Integer freeOccupancy) {
		super();
		this.code = code;
		this.name = name;
		this.capacity = capacity;
		this.elements = elements;
		this.freeOccupancy = freeOccupancy;
	}
	
	// Getters y Setters

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

	public Integer getFreeOccupancy() {
		return freeOccupancy;
	}

	public void setFreeOccupancy(Integer freeOccupancy) {
		this.freeOccupancy = freeOccupancy;
	}

	// toString()
	
	@Override
	public String toString() {
		return "BookSummaryModel [code=" + code + ", name=" + name + ", capacity=" + capacity + ", elements=" + elements
				+ ", freeOccupation=" + freeOccupancy + "]";
	}
	
	
}
