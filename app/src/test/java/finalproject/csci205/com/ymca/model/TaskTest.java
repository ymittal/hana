package finalproject.csci205.com.ymca.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of {@link Task} class
 */
public class TaskTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Tests the functionality of {@link Task#compareTo(Object)} method
     * for different scenarios between two tasks
     *
     * @throws Exception
     * @author Yash
     */
    @Test
    public void compareTo() throws Exception {
        Task task1 = new Task();
        Task task2 = new Task();

        task1.setIsComplete(true);
        assertEquals(1, task1.compareTo(task2));

        task2.setIsComplete(true);
        assertEquals(-1, task1.compareTo(task2));

        task1.setDueDate(new Date(0L));
        assertEquals(1, task1.compareTo(task2));

        task2.setDueDate(new Date(5000L));
        assertEquals(-1, task1.compareTo(task2));

        task1.setDueDate(new Date(5000L));
        assertEquals(0, task1.compareTo(task2));
    }
}