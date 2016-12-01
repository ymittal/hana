package finalproject.csci205.com.ymca;

import org.junit.Test;

import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.GTDPresenter;

import static org.junit.Assert.assertEquals;

/**
 *  Class for testing the functionality of various GTDPresenter
 *  methods.
 *
 * Created by ala021 on 11/13/16.
 */
public class GTDPresenterTest {


    /**
     * Unit Test for adding tasks to the GTD Presenter
     *
     * @throws Exception
     */
    @Test
    public void test_AddTask() throws Exception {
        GTDPresenter test = new GTDPresenter();
        Task testTask = new Task();
        test.addTask(testTask, true);
        int expectedListLength = 1;
        int actual = test.getTasks().size();
        assertEquals(expectedListLength, actual);
    }

    /**
     * Unit Test for adding tasks to the GTD Presenter
     *
     * @throws Exception
     */
    @Test
    public void test_removeTask() throws Exception {
        GTDPresenter test = new GTDPresenter();
        Task testTask = new Task();
        test.addTask(testTask, true);
        test.removeTask(0);
        int expectedListLength = 0;
        int actual = test.getTasks().size();
        assertEquals(expectedListLength, actual);
    }
}
