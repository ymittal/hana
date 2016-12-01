package finalproject.csci205.com.ymca.presenter;

import android.support.test.rule.ActivityTestRule;

import com.orm.SugarContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.view.NavActivity;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the functionality of {@link GTDPresenter}
 *
 * Created by Alekzander
 */
public class GTDPresenterInstrumentationTest {

    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    @Before
    public void setUp() throws Exception {
        SugarContext.init(activityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        SugarContext.terminate();
    }


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