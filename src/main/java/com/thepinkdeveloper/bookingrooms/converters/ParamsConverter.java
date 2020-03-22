package com.thepinkdeveloper.bookingrooms.converters;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.thepinkdeveloper.bookingrooms.staticdata.Elements;

@Component("paramsConverter")
public class ParamsConverter implements Elements {

	public Map<Boolean, LocalDate> bookARoomDateParamConverter(Integer day, Integer month,	Integer year) {
		Map<Boolean, LocalDate> result = new HashMap<>();
		try {
			LocalDate date = LocalDate.of(year, month, day);
			result.put(true, date);
		} catch(Exception e) {
			e.printStackTrace();
			result.put(false, null);
		}
		return result;
	}
			
	public Map<Boolean, Integer> bookARoomFromTimeParamConverter(Integer fromHour, Integer fromMinute) {
		Map<Boolean, Integer> result = new HashMap<>();
		try {
			if (fromHour < 7 || fromHour > 21 || fromMinute % 15 != 0 || fromMinute > 45 ) {
				result.put(false, -1);	
			} else {
				Integer computedFromHour = (fromHour - 7) * 4 + fromMinute / 15;
				result.put(true, computedFromHour);
			}
		} catch(Exception e) {
			e.printStackTrace();
			result.put(false, null);
		}
		return result;
	}
	
	public Map<Boolean, Integer> bookARoomToTimeParamConverter(Integer toHour,	Integer toMinute) {
		Map<Boolean, Integer> result = new HashMap<>();
		try {
			if (toHour < 7 || toHour > 22 || toMinute % 15 != 0 || toMinute > 45 ||
					(toHour == 22 && toMinute != 0)) {
				result.put(false, -1);	
			} else {
				Integer computedToHour = ((toHour - 7) * 4 + toMinute / 15) - 1;
				result.put(true, computedToHour);
			}
		} catch(Exception e) {
			e.printStackTrace();
			result.put(false, null);
		}
		return result;
	}
			
	public Integer bookARoomElementsConverter(
			Boolean needTv,
			Boolean needPr,
			Boolean needDb,
			Boolean needVc,
			Boolean needWf) {
		
		Integer result = 1;
		
		if (needTv) result *= TV;
		if (needPr) result *= PROYECTOR;
		if (needDb) result *= DIGITAL_BLACKBOARD;
		if (needVc) result *= VIDEOCONFERENCE;
		if (needWf) result *= WIFI;
		
		return result;

	}
}
