package todo.advance.blindsight.util.generate.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateGenerator {
// =========================================================================================================================
	// Create current date and format current date & time 
	public static String getCurrentDate () {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss");
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
// =========================================================================================================================
	// Create current date and format current date & time 
	public static String getCurrentDateWithoutHMS () {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
// =========================================================================================================================
	// Create current date and format current date & time 
	public static String getCurrentDateDetails () {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
// =========================================================================================================================
	// Create local current date and format current date & time   
    public static Date getLocalCurrentDate() {
        // default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = LocalDate.now();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

        return date;
    }
}
