package com.thepinkdeveloper.bookingrooms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.cfg.context.ConstraintDefinitionContext.ValidationCallable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thepinkdeveloper.bookingrooms.converters.ParamsConverter;
import com.thepinkdeveloper.bookingrooms.converters.RoomConverter;
import com.thepinkdeveloper.bookingrooms.entities.Room;
import com.thepinkdeveloper.bookingrooms.models.RoomModel;
import com.thepinkdeveloper.bookingrooms.services.impl.RoomServiceImpl;
import com.thepinkdeveloper.bookingrooms.staticdata.Elements;

@RestController
@RequestMapping("/room")
public class RoomController implements Elements {
	
	@Autowired
	@Qualifier("roomServiceImpl")
	private RoomServiceImpl roomServiceImpl;
	
	@Autowired
	@Qualifier("roomConverter")
	private RoomConverter roomConverter;
	
	@Autowired
	@Qualifier("paramsConverter")
	private ParamsConverter paramsConverter;
	
	@GetMapping(value = "/getAll")
	public List<RoomModel> getAll() {
		List<RoomModel> roomModelList = new ArrayList<>();
		List<Room> roomList = roomServiceImpl.getAllRooms();
		roomList.forEach(room -> roomModelList.add(roomConverter.entity2model(room)));
		return roomModelList;
	}

	@GetMapping(value = "/create")
	public String create(
		@RequestParam(name = "code", required = true) String code,
		@RequestParam(name = "name", required = true) String name,
		@RequestParam(name = "capacity", required = true) Integer capacity,
		@RequestParam(name = "hasTv", required = true) Boolean hasTv,
		@RequestParam(name = "hasProyector", required = true) Boolean hasPr,
		@RequestParam(name = "hasDigitalBlackboard", required = true) Boolean hasDb,
		@RequestParam(name = "hasVideoconference", required = true) Boolean hasVc,
		@RequestParam(name = "hasWifi", required = true) Boolean hasWf) {
		
		Integer elements = paramsConverter.bookARoomElementsConverter(hasTv, hasPr, hasDb, hasVc, hasWf);
		
		Room room = new Room(code, name, capacity, elements);
		boolean result = roomServiceImpl.saveARoom(room);
		if (result) {
			return "Se ha creado la Sala " + room.getName() + " con código " + room.getCode();
		} else {
			return "Se ha producido un error durante la creación de la sala";
		}
		
	}
	
	@GetMapping(value = "/update")
	public String update(
		@RequestParam(name = "code", required = true) String code,
		@RequestParam(name = "name", required = false) String name,
		@RequestParam(name = "capacity", required = false) Integer capacity,
		@RequestParam(name = "hasTv", required = false) Boolean hasTv,
		@RequestParam(name = "hasProyector", required = false) Boolean hasPr,
		@RequestParam(name = "hasDigitalBlackboard", required = false) Boolean hasDb,
		@RequestParam(name = "hasVideoconference", required = false) Boolean hasVc,
		@RequestParam(name = "hasWifi", required = false) Boolean hasWf) {
		
		Room room = roomServiceImpl.findByCode(code);
		
		Boolean previousHasTv = room.getElements() % TV == 0;
		Boolean previousHasPr = room.getElements() % PROYECTOR == 0;
		Boolean previousHasDb = room.getElements() % DIGITAL_BLACKBOARD == 0;
		Boolean previousHasVc = room.getElements() % VIDEOCONFERENCE == 0;
		Boolean previousHasWf = room.getElements() % WIFI == 0;
		
		Boolean newHasTv = (hasTv != null) ? hasTv : previousHasTv;
		Boolean newHasPr = (hasPr != null) ? hasPr : previousHasPr;
		Boolean newHasDb = (hasDb != null) ? hasDb : previousHasDb;
		Boolean newHasVc = (hasVc != null) ? hasVc : previousHasVc;
		Boolean newHasWf = (hasWf != null) ? hasWf : previousHasWf;

		Integer newElements = paramsConverter.bookARoomElementsConverter(newHasTv, newHasPr, newHasDb, newHasVc, newHasWf); 
		String newName = (name != null) ? name : room.getName();
		Integer newCapacity = (capacity != null) ? capacity : room.getCapacity();
		
		room.setName(newName);
		room.setCapacity(newCapacity);
		room.setElements(newElements);
		
		boolean result = roomServiceImpl.saveARoom(room);
		if (result) {
			return "Se ha actualizado la Sala " + room.getName() + " con código " + room.getCode();
		} else {
			return "Se ha producido un error durante la actualización de la sala";
		}
	}
	
	@GetMapping(value = "/delete")
	public String delete(@RequestParam(name = "code", required = true) String code) {
		
		Room room = roomServiceImpl.findByCode(code);
		Boolean result = false;
		if (code != null) {
			result = roomServiceImpl.deleteARoom(room);
		}
		
		if (result) {
			return "La sala " + room.getName() + " (" + room.getCode() 
				+ ") se ha elimano con éxito";
		} else {
			return "Se ha producido un error eliminando la sala solicitada";
		}
	}
	

	
	
}
