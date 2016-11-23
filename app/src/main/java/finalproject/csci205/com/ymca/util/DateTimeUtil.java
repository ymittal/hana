package finalproject.csci205.com.ymca.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class to get user-friendly date representations
 */
public class DateTimeUtil {

    public static final int MILLIS_IN_SECOND = 1000;
    public static final int SECS_IN_DAY = (24 * 60 * 60);
    public static final int SECS_IN_HOUR = (60 * 60);
    public static final int SECS_IN_MINUTE = (60);
    /**
     * {@link SimpleDateFormat} to represent a user-friendly date notation,
     * e.g. 10:50 PM on April 18, 2016 (Tuesday)
     *
     * @see <a href="http://stackoverflow.com/questions/2912149/how-can-i-insert-special-characters-in-simpledateformat">
     * Stack Overflow - How can I insert special characters in SimpleDateFormat?</a>
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a 'on' MMM dd, yyyy '('EEEE')'", Locale.US);

    /**
     * @param date {@link Date} object
     * @return user readable date notation
     * @author Yash
     */
    public static String getReadableDate(Date date) {
        return sdf.format(date);
    }

    /**
     * @param date {@link Date} object
     * @return
     * @author Yash
     */
    public static String convertDateToTimeRemaining(Date date) {
        long secondsLeft = (date.getTime() - System.currentTimeMillis()) / MILLIS_IN_SECOND;

        if (secondsLeft <= 0) {
            return "Task overdue";
        } else if (secondsLeft < SECS_IN_MINUTE) {
            return "Less than a minute remaining";
        } else {
            int daysLeft = (int) (secondsLeft / SECS_IN_DAY);
            secondsLeft = secondsLeft % SECS_IN_DAY;
            int hoursLeft = (int) (secondsLeft / SECS_IN_HOUR);
            secondsLeft = secondsLeft % SECS_IN_HOUR;
            int minutesLeft = (int) (secondsLeft / SECS_IN_MINUTE);

            StringBuilder sb = new StringBuilder()
                    .append(formatTimeUnit(daysLeft, "dy")).append(" ")
                    .append(formatTimeUnit(hoursLeft, "hr")).append(" ")
                    .append(formatTimeUnit(minutesLeft, "min")).append(" ")
                    .append("remaining");

            // removes leading and trailing spaces, and replaces consecutive spaces with a single space
            return sb.toString().trim().replaceAll("\\s+", " ");
        }
    }

    /**
     * @param period
     * @param unit
     * @return
     * @author Yash
     */
    private static String formatTimeUnit(int period, String unit) {
        if (period == 0) {
            return "";
        } else if (period == 1) {
            return Integer.toString(period) + " " + unit;
        } else {
            return Integer.toString(period) + " " + unit + "s";
        }
    }
}
