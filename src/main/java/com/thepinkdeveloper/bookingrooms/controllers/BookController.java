package com.thepinkdeveloper.bookingrooms.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thepinkdeveloper.bookingrooms.converters.ParamsConverter;
import com.thepinkdeveloper.bookingrooms.models.AdvancedRoomModel;
import com.thepinkdeveloper.bookingrooms.services.impl.BookServiceImpl;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	@Qualifier("bookServiceImpl")
	private BookServiceImpl bookServiceImpl;
	
	@Autowired
	@Qualifier("paramsConverter")
	private ParamsConverter paramsConverter;
	
	@GetMapping(value = "/room")
	public String bookARoom(
			@RequestParam(name = "day", required = true) Integer day,
			@RequestParam(name = "month", required = true) Integer month,
			@RequestParam(name = "year", required = true) Integer year,
			@RequestParam(name = "members", required = true) Integer members,
			@RequestParam(name = "fromHour", required = true) Integer fromHour,
			@RequestParam(name = "fromMinute", required = true) Integer fromMinute,
			@RequestParam(name = "toHour", required = true) Integer toHour,
			@RequestParam(name = "toMinute", required = true) Integer toMinute,
			@RequestParam(name = "needTv", required = true) Boolean needTv,
			@RequestParam(name = "needProyector", required = true) Boolean needPr,
			@RequestParam(name = "needDigitalBlackboard", required = true) Boolean needDb,
			@RequestParam(name = "needVideoconference", required = true) Boolean needVc,
			@RequestParam(name = "needWifi", required = true) Boolean needWf) {
		
		Map<Boolean, LocalDate> dateConverted = paramsConverter.bookARoomDateParamConverter(day, month, year);
		Map<Boolean, Integer> fromTimeConverted = paramsConverter.bookARoomFromTimeParamConverter(fromHour, fromMinute);
		Map<Boolean, Integer> toTimeConverted = paramsConverter.bookARoomToTimeParamConverter(toHour, toMinute);
		Integer elements = paramsConverter.bookARoomElementsConverter(needTv, needPr, needDb, needVc, needWf);
		
		LocalDate dateOk = null;
		Integer fromTimeOk = null;
		Integer toTimeOk = null;
		Integer elementsOk = elements;
		
		if (dateConverted.containsKey(false)) {
			return "Los parámetros de fecha solicitados son incorrectos";
		} else {
			dateOk = dateConverted.get(true);
		}
		
		if (fromTimeConverted.containsKey(false)) {
			if (fromTimeConverted.get(false) == -1) {
				return "Sólo es posible reservar salas de 7:00h a 22:00h en tramos de 15 minutos";
			} else {
				return "Algunos de los parámetros de hora solicitados son incorrectos";
			}
		} else {
			fromTimeOk = fromTimeConverted.get(true);
		}
		
		if (toTimeConverted.containsKey(false)) {
			if (toTimeConverted.get(false) == -1) {
				return "Sólo es posible reservar salas de 7:00h a 22:00h en tramos de 15 minutos";
			} else {
				return "Algunos de los parámetros de hora solicitados son incorrectos";
			}
		} else {
			toTimeOk = toTimeConverted.get(true);
		}
		
		if (fromTimeOk > toTimeOk) {
			return "No se puede solicitar una hora de finalización anterior a la hora de comienzo";
		}
		
		Map<Boolean, AdvancedRoomModel> result = bookServiceImpl.bookARoom(dateOk, members, fromTimeOk, toTimeOk, elementsOk);
		if (result.containsKey(true)) {
			return "La reserva se realizó satisfactoriamente en sala: " + result.get(true).getName() 
					+ " - Código: " +  result.get(true).getCode();
		} else {
			if (result.get(false) == null) {
				return "Se ha producido un error indeterminado en la aplicación que ha impedido la realización de la reserva.";
			} else {
				return "No hay salas disponibles con las características demandadas.";
			}
		}
	}
}
