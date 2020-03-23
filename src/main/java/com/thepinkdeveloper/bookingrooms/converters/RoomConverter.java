package com.thepinkdeveloper.bookingrooms.converters;

import org.springframework.stereotype.Component;

import com.thepinkdeveloper.bookingrooms.entities.Room;
import com.thepinkdeveloper.bookingrooms.models.AdvancedRoomModel;
import com.thepinkdeveloper.bookingrooms.models.RoomModel;

@Component("roomConverter")
public class RoomConverter {
	
	public AdvancedRoomModel entity2model(Room room, Integer freeOccupancy) {
		AdvancedRoomModel roomModel = new AdvancedRoomModel();
		roomModel.setCode(room.getCode());
		roomModel.setName(room.getName());
		roomModel.setCapacity(room.getCapacity());
		roomModel.setElements(room.getElements());
		roomModel.setFreeOccupancy(freeOccupancy);
		return roomModel;
	}
	
	public RoomModel entity2model(Room room) {
		RoomModel roomModel = new RoomModel();
		roomModel.setCode(room.getCode());
		roomModel.setName(room.getName());
		roomModel.setCapacity(room.getCapacity());
		roomModel.setElements(room.getElements());
		return roomModel;
	}
}
