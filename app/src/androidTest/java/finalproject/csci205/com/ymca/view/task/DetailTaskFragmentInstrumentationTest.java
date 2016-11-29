package finalproject.csci205.com.ymca.view.task;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.view.NavActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

/**
 * Class to test {@link DetailTaskFragment} UI functionality
 */
public class DetailTaskFragmentInstrumentationTest {

    public static final String DUMMY_TASK = "Dummy Task";
    public static final String DUMMY_SUBTASK = "Dummy subtask";

    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    /**
     * Automates adding a {@link finalproject.csci205.com.ymca.model.Task} and launching
     * the {@link DetailTaskFragment} by clicking on the first {@link RecyclerView} item
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        String sSave = activityTestRule.getActivity().getString(R.string.negative_btn_save);
        onView(withId(R.id.fab)).perform(click());
        onView(withText(sSave)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withId(R.id.etAddTask)).perform(typeText(DUMMY_TASK), closeSoftKeyboard());
        onView(withText(sSave)).perform(click());
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    /**
     * Automates closing the keyboard and going back to {@link GTDFragment} to delete the
     * recently added {@link finalproject.csci205.com.ymca.model.Task}
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        onView(withId(R.id.fragmentDetailTask)).perform(closeSoftKeyboard());
        onView(withId(R.id.fragmentDetailTask)).perform(ViewActions.pressBack());
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
    }

    /**
     * Tests whether a {@link finalproject.csci205.com.ymca.model.Subtask} is successfully
     * added to the list of subtasks under {@link finalproject.csci205.com.ymca.model.Task}
     */
    @Test
    public void checkAddSubtask() {
        onView(withId(R.id.rvSubtasks)).check(new RecyclerViewItemCountAssertion(0));
        onView(withId(R.id.addSubtaskBtn)).perform(click());

        onView(withId(R.id.etSubtask)).perform(typeText("")).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.rvSubtasks)).check(new RecyclerViewItemCountAssertion(0));

        onView(withId(R.id.etSubtask)).perform(typeText(DUMMY_SUBTASK)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.rvSubtasks)).check(new RecyclerViewItemCountAssertion(1));
    }

    /**
     * Tests whether a {@link finalproject.csci205.com.ymca.model.Subtask} is removed
     * when its corresponding cancel button is clicked
     */
    @Test
    public void checkRemoveSubtask() {
        checkAddSubtask();
        onView(withId(R.id.btnCancel)).perform(click());
        onView(withId(R.id.rvSubtasks)).check(new RecyclerViewItemCountAssertion(0));
    }

    /**
     * A {@link ViewAssertion} class to quickly assert the number of items in a
     * {@link RecyclerView}
     *
     * @see <a href="http://stackoverflow.com/questions/36399787/espresso-count-recyclerview-items">
     * Espresso - Count recyclerview items</a>
     */
    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

}