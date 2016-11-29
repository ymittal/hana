package finalproject.csci205.com.ymca.view.task.dialog;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

/**
 * Class to test {@link AddQuickTaskDialog} UI functionality
 */
public class AddQuickTaskDialogInstrumentationTest {

    public static final String DUMMY_TASK = "Dummy Task";

    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    private String sSave;
    private String sEdit;

    /**
     * A custom ViewMatcher for views with errors which are not supported by the library
     *
     * @param stringMatcher {@link Matcher<String>} containing error string of {@link TextInputLayout}
     * @return {@link Matcher<View>} object
     * @see <a href="http://stackoverflow.com/questions/37073050/how-can-you-check-a-textinputlayouts-error-with-espresso">
     * Stack Overflow - How can you check a TextinputLayout's error with Espresso</a>
     */
    private static Matcher<View> withErrorInInputLayout(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);

        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {
            String actualError = "";

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(TextInputLayout textInputLayout) {
                CharSequence error = textInputLayout.getError();
                if (error != null) {
                    actualError = error.toString();
                    return stringMatcher.matches(actualError);
                }
                return false;
            }
        };
    }

    /**
     * Automates click on {@link android.support.design.widget.FloatingActionButton}
     * present on the {@link NavActivity}
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        sSave = activityTestRule.getActivity().getString(R.string.negative_btn_save);
        sEdit = activityTestRule.getActivity().getString(R.string.positive_btn_edit);

        onView(withId(R.id.fab)).perform(click());
        onView(withText(sSave)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Tests whether {@link finalproject.csci205.com.ymca.view.task.DetailTaskFragment} opens up
     * successfully when user enters a {@link finalproject.csci205.com.ymca.model.Task} title
     */
    @Test
    public void checkAddDetailTask() {
        onView(withId(R.id.etAddTask)).perform(typeText(DUMMY_TASK), closeSoftKeyboard());
        onView(withText(sEdit)).perform(click());
        onView(withId(R.id.fragmentDetailTask)).check(matches(isDisplayed()));
        onView(withId(R.id.fragmentDetailTask)).perform(ViewActions.pressBack());
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
    }

    /**
     * Test to check whether a {@link finalproject.csci205.com.ymca.model.Task} is quickly
     * added through {@link AddQuickTaskDialog}
     */
    @Test
    public void checkQuickAddTask() {
        onView(withId(R.id.etAddTask)).perform(typeText(DUMMY_TASK), closeSoftKeyboard());
        onView(withText(sSave)).perform(click());
        onView(withId(R.id.rvTasks)).check(matches(hasDescendant(withText(DUMMY_TASK))));
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
    }

    /**
     * Validates user input and checks if {@link TextInputLayout} has an error when user enters
     * an empty {@link finalproject.csci205.com.ymca.model.Task} title
     */
    @Test
    public void validateAddTaskEditText() {
        onView(withText(sSave)).perform(click());
        onView(withId(R.id.tilAddTask)).check(
                matches(withErrorInInputLayout(is(activityTestRule.getActivity().getString(R.string.error_til_add_task))))
        );
    }
}