package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
public class DateUtil {

    // Array of allowed date formats
    private static final String[] DATE_FORMATS = {"dd/MM/yyyy", "yyyy-MM-dd"};

    public static Date parseDate(String dateStr) {
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false);
                return sdf.parse(dateStr);
            } catch (ParseException ignored) {
                // try next format
            }
        }
        System.out.println("Invalid date format. Please use dd/MM/yyyy or yyyy-MM-dd.");
        return null;
    }
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    public static String formatDate(Date date) {
        // Default formatting to dd/MM/yyyy
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
