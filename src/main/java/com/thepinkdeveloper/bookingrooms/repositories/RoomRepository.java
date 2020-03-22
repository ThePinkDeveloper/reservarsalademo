package com.thepinkdeveloper.bookingrooms.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thepinkdeveloper.bookingrooms.entities.Room;

@Repository("roomRepository")
public interface RoomRepository extends JpaRepository<Room, Serializable>{
	
	public abstract Room findByCode(String code);

}
