package com.example.learning.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeUtils {

	public static Timestamp addMinutesToTime(int minutes) {
		LocalDateTime futureTime = LocalDateTime.now().plusMinutes(minutes);
		return Timestamp.valueOf(futureTime);
	}

}
