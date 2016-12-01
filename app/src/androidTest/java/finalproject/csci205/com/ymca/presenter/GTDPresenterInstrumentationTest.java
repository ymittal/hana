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

    private GTDPresenter gtdPresenter;

    @Before
    public void setUp() throws Exception {
        SugarContext.init(activityTestRule.getActivity());

        Task.deleteAll(Task.class);
        gtdPresenter = new GTDPresenter();
    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     * Unit Test for adding tasks to the GTD Presenter
     *
     * @throws Exception
     */
    @Test
    public void test_AddTask() throws Exception {
        gtdPresenter.addTask(new Task(), true);
        int actualLength = gtdPresenter.getTasks().size();
        int expectedLength = 1;
        assertEquals(expectedLength, actualLength);
    }


    /**
     * Unit Test for adding tasks to the GTD Presenter
     *
     * @throws Exception
     */
    @Test
    public void test_removeTask() throws Exception {
        gtdPresenter.addTask(new Task(), false);
        int actualLength = gtdPresenter.getTasks().size();
        int expectedLength = 1;
        assertEquals(expectedLength, actualLength);

        gtdPresenter.removeTask(0);
        actualLength = gtdPresenter.getTasks().size();
        expectedLength = 0;
        assertEquals(expectedLength, actualLength);
    }
}