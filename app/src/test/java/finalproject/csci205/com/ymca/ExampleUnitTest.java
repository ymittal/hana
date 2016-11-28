package finalproject.csci205.com.ymca;

import org.junit.Test;

import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.GTDPresenter;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit cd_notification_layout, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    /**
     * Unit Test for adding tasks to the GTD Presenter
     *
     * @throws Exception
     */
    @Test
    public void addTask() throws Exception {
        GTDPresenter test = new GTDPresenter();
        Task testTask = new Task();
        test.addTask(testTask, true);
        int expectedListLength = 1;
        int actual = test.getTasks().size();
        assertEquals(expectedListLength, actual);

    }
}