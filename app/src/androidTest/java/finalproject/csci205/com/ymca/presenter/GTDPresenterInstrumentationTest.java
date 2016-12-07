package finalproject.csci205.com.ymca.presenter;

import android.support.test.rule.ActivityTestRule;

import com.orm.SugarContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.view.NavActivity;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the functionality of {@link GTDPresenter}
 *
 * @author Alekzander and Yash
 */
public class GTDPresenterInstrumentationTest {

    /**
     * Testing constants
     */
    public static final String DUMMY_TASK_2 = "Dummy Task 2";
    public static final String DUMMY_TASK_1 = "Dummy Task 1";
    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    /**
     * GTD refrence
     */
    private GTDPresenter gtdPresenter;

    /**
     * Initializes {@link SugarContext} (SugarORM database) and clears
     * stored {@link Task} objects
     *
     * @throws Exception
     * @author Aleks
     */
    @Before
    public void setUp() throws Exception {
        SugarContext.init(activityTestRule.getActivity());

        Task.deleteAll(Task.class);
        gtdPresenter = new GTDPresenter(activityTestRule.getActivity());
    }

    /**
     * Clears existing {@link Task} objects
     *
     * @throws Exception
     * @author Aleks
     */
    @After
    public void tearDown() throws Exception {
        Task.deleteAll(Task.class);
    }


    /**
     * Unit test for adding tasks through {@link GTDPresenter}
     *
     * @throws Exception
     * @author Aleks
     */
    @Test
    public void testAddTask() throws Exception {
        Task newTask = new Task();
        gtdPresenter.addTask(newTask, true);

        int actualLength = gtdPresenter.taskSize();
        int expectedLength = 1;
        assertEquals(expectedLength, actualLength);
    }


    /**
     * Unit test for removing tasks through {@link GTDPresenter}
     *
     * @throws Exception
     * @author Aleks
     */
    @Test
    public void testRemoveTask() throws Exception {
        Task newTask = new Task();
        gtdPresenter.addTask(newTask, false);

        int actualLength = gtdPresenter.taskSize();
        int expectedLength = 1;
        assertEquals(expectedLength, actualLength);

        gtdPresenter.removeTask(0);
        actualLength = gtdPresenter.taskSize();
        expectedLength = 0;
        assertEquals(expectedLength, actualLength);
    }

    /**
     * Gets a list of {@link Task} through {@link GTDPresenter}
     *
     * @throws Exception
     * @author Yash
     */
    @Test
    public void getTasks() throws Exception {
        Task newTask1 = new Task(DUMMY_TASK_1);
        Task newTask2 = new Task(DUMMY_TASK_2);
        gtdPresenter.addTask(newTask1, false);
        gtdPresenter.addTask(newTask2, false);

        List<Task> expectedTasks = Task.listAll(Task.class);
        List<Task> resultTasks = gtdPresenter.getTasks();
        Collections.reverse(resultTasks);   // to consider for Task.compareTo() method which auto-orders newly added Tasks

        assertEquals(expectedTasks.size(), resultTasks.size());
        assertEquals(expectedTasks.get(0).getTitle(), resultTasks.get(0).getTitle());
        assertEquals(expectedTasks.get(1).getTitle(), resultTasks.get(1).getTitle());
    }

    /**
     * Unit test to check off a task through {@link GTDPresenter}
     *
     * @throws Exception
     * @author Aleks
     */
    @Test
    public void taskChecked() throws Exception {
        Task newTask = new Task();
        gtdPresenter.addTask(newTask, false);

        gtdPresenter.taskChecked(0, true);
        boolean expectedCheckedStatus = Task.listAll(Task.class).get(0).isComplete();
        assertEquals(expectedCheckedStatus, true);

        gtdPresenter.taskChecked(0, false);
        expectedCheckedStatus = Task.listAll(Task.class).get(0).isComplete();
        assertEquals(expectedCheckedStatus, false);
    }

}