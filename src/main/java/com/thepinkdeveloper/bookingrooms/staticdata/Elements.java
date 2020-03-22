package com.thepinkdeveloper.bookingrooms.staticdata;

import java.util.Arrays;
import java.util.List;

public interface Elements {
	
	public static int MAX_ELEMENTS = 5;
	
	public static int TV = 2;
	public static int PROYECTOR = 3;
	public static int DIGITAL_BLACKBOARD = 5;
	public static int VIDEOCONFERENCE = 7;
	public static int WIFI = 11;
	
	public static List<Integer> listElements = Arrays.asList(
			TV, PROYECTOR, DIGITAL_BLACKBOARD, VIDEOCONFERENCE, WIFI);
	
}
