package com.thepinkdeveloper.bookingrooms.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.thepinkdeveloper.bookingrooms.entities.Room;
import com.thepinkdeveloper.bookingrooms.repositories.RoomRepository;
import com.thepinkdeveloper.bookingrooms.services.RoomService;

@Service("roomServiceImpl")
public class RoomServiceImpl implements RoomService {

	@Autowired
	@Qualifier("roomRepository")
	private RoomRepository roomRepository;
	
	@Override
	public Room findByCode(String code) {
		return roomRepository.findByCode(code);
	}
	
	@Override
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	@Override
	public boolean saveARoom(Room room) {
		boolean hadSuccess = false;
		try {
			roomRepository.save(room);
			hadSuccess = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return hadSuccess;
	}

	@Override
	public boolean deleteARoom(Room room) {
		boolean hadSuccess = false;
		try {
			roomRepository.delete(room);
			hadSuccess = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return hadSuccess;
	}

	@Override
	public boolean updateARoom(Room room) {
		boolean hadSuccess = false;
		try {
			Room roomToUpdate = roomRepository.getOne(room.getId());
			if (roomToUpdate != null) {
				roomRepository.save(room);
				hadSuccess = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return hadSuccess;
	}

}
