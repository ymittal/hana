package finalproject.csci205.com.ymca.presenter;

import android.support.test.rule.ActivityTestRule;

import com.orm.SugarContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.view.NavActivity;

import static org.junit.Assert.assertEquals;

/**
 * Created by ym012 on 11/30/2016.
 */
public class DetailTaskPresenterInstrumentationTest {

    public static final String DUMMY_TASK = "Dummy Task";
    public static final String DUMMY_SUBTASK = "Dummy Subtask";
    private static final String DUMMY_SUBTASK_2 = "Dummy Subtask 2";

    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    private DetailTaskPresenter detailTaskPresenter;
    private Task task;

    @Before
    public void setUp() throws Exception {
        SugarContext.init(activityTestRule.getActivity());

        Task.deleteAll(Task.class);
        Subtask.deleteAll(Subtask.class);
        detailTaskPresenter = new DetailTaskPresenter();

        addDummyTask();
    }

    @After
    public void tearDown() throws Exception {
        Task.deleteAll(Task.class);
        Subtask.deleteAll(Subtask.class);
    }

    private void addDummyTask() {
        GTDPresenter gtdPresenter = new GTDPresenter();
        task = new Task(DUMMY_TASK);
        gtdPresenter.addTask(task, false);
    }

    @Test
    public void getSubtasks() throws Exception {
        Subtask newSubtask = new Subtask(task.getId(), DUMMY_SUBTASK);
        Subtask newSubtask2 = new Subtask(task.getId(), DUMMY_SUBTASK_2);
        detailTaskPresenter.addSubtask(newSubtask);
        detailTaskPresenter.addSubtask(newSubtask2);

        List<Subtask> expectedTasks = Subtask.listAll(Subtask.class);
        List<Subtask> resultTasks = detailTaskPresenter.getSubtasks();

        assertEquals(expectedTasks.size(), resultTasks.size());
        assertEquals(expectedTasks.get(0).getTitle(), resultTasks.get(0).getTitle());
        assertEquals(expectedTasks.get(1).getTitle(), resultTasks.get(1).getTitle());
    }

    @Test
    public void addSubtask() throws Exception {
        Subtask newSubtask = new Subtask(task.getId(), DUMMY_SUBTASK);
        detailTaskPresenter.addSubtask(newSubtask);

        int actualLength = detailTaskPresenter.getNumSubtasks();
        int expectedLength = 1;
        assertEquals(expectedLength, actualLength);
    }

    @Test
    public void removeTask() throws Exception {
        Subtask newSubtask = new Subtask();
        detailTaskPresenter.addSubtask(newSubtask);

        int actualLength = detailTaskPresenter.getNumSubtasks();
        int expectedLength = 1;
        assertEquals(expectedLength, actualLength);

        detailTaskPresenter.removeSubtask(0);
        actualLength = detailTaskPresenter.getNumSubtasks();
        expectedLength = 0;
        assertEquals(expectedLength, actualLength);
    }

    @Test
    public void setDescription() throws Exception {
        Subtask newSubtask = new Subtask(task.getId(), DUMMY_SUBTASK);
        detailTaskPresenter.addSubtask(newSubtask);

        String expectedSubtaskDesc = Subtask.listAll(Subtask.class).get(0).getTitle();
        assertEquals(expectedSubtaskDesc, DUMMY_SUBTASK);
    }
}