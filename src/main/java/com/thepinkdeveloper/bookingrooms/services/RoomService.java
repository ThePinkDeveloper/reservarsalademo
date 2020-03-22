package com.thepinkdeveloper.bookingrooms.services;

import java.util.List;

import com.thepinkdeveloper.bookingrooms.entities.Room;

public interface RoomService {
	
	public abstract List<Room> getAllRooms();
	
	public abstract boolean saveARoom(Room room);
	
	public abstract boolean deleteARoom(Room room);
	
	public abstract boolean updateARoom(Room room);

}
