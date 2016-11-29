package finalproject.csci205.com.ymca.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Class to test {@link DateTimeUtil}
 */
public class DateTimeUtilTest {

    /**
     * Number of milliseconds in a second
     */
    public static final int MILLIS_IN_SECOND = 1000;
    /**
     * Number of milliseconds in a day
     */
    public static final int MILLIS_IN_DAY = (24 * 60 * 60 * 1000);
    /**
     * Number of milliseconds in an hour
     */
    public static final int MILLIS_IN_HOUR = (60 * 60 * 1000);
    /**
     * Number of milliseconds in a minute
     */
    public static final int MILLIS_IN_MINUTE = (60 * 1000);

    /**
     * Test for {@link DateTimeUtil#convertDateToTimeRemaining(Date)}
     *
     * @throws Exception
     */
    @Test
    public void convertDateToTimeRemaining() throws Exception {
        Date testDate = new Date(System.currentTimeMillis() - 100);
        String sTimeRemaining = DateTimeUtil.convertDateToTimeRemaining(testDate);
        String expected = "Task overdue";
        assertEquals(expected, sTimeRemaining);

        // offset by 50 seconds
        testDate = new Date(System.currentTimeMillis() + 50 * MILLIS_IN_SECOND);
        sTimeRemaining = DateTimeUtil.convertDateToTimeRemaining(testDate);
        expected = "Less than a minute remaining";
        assertEquals(expected, sTimeRemaining);

        // offset by 2 days and 50 seconds
        testDate = new Date(System.currentTimeMillis() + 2 * MILLIS_IN_DAY + 50 * MILLIS_IN_SECOND);
        sTimeRemaining = DateTimeUtil.convertDateToTimeRemaining(testDate);
        expected = "2 dys remaining";
        assertEquals(expected, sTimeRemaining);

        // offset by an hour, 5 minutes and 50 seconds
        testDate = new Date(System.currentTimeMillis() + MILLIS_IN_HOUR + 5 * MILLIS_IN_MINUTE + 50 * MILLIS_IN_SECOND);
        sTimeRemaining = DateTimeUtil.convertDateToTimeRemaining(testDate);
        expected = "1 hr 5 mins remaining";
        assertEquals(expected, sTimeRemaining);
    }

    /**
     * Test for {@link DateTimeUtil#getReadableDate(Date)}
     *
     * @throws Exception
     */
    @Test
    public void getReadableDate() throws Exception {
        String result = DateTimeUtil.getReadableDate(new Date(0));
        String expected = "07:00 PM on Dec 31, 1969 (Wednesday)";   // converted to Locale.US
        assertEquals(expected, result);
    }
}