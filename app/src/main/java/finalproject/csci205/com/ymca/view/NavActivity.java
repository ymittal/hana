package finalproject.csci205.com.ymca.view;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.util.NotificationUtil;
import finalproject.csci205.com.ymca.view.module.pomodoro.PomodoroFragment;
import finalproject.csci205.com.ymca.view.module.pomodoro.PomodoroSettingsFragment;
import finalproject.csci205.com.ymca.view.module.tenminhack.TenMinuteFragment;
import finalproject.csci205.com.ymca.view.task.DetailTaskFragment;
import finalproject.csci205.com.ymca.view.task.GTDFragment;

/**
 * {@link android.app.Activity} to hold the hamburger menu allowing user to
 * switch between different productivity techniques
 *
 * @author Yash, Malachi, Aleks, and Charles
 */
public class NavActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        GTDFragment.OnFragmentInteractionListener {

    /**
     * Top-level container for {@link NavigationView}
     */
    private DrawerLayout drawer;
    /**
     * Navigation menu
     */
    private NavigationView navigationView;

    /**
     * Instance Fragments
     */
    private GTDFragment gtdFragment;
    private PomodoroFragment pomoFragment;
    private TenMinuteFragment tenMinFragment;

    /**
     * Sets up activity user interface and controls
     *
     * @param savedInstanceState
     * @author Charles and Yash
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        initUI();
        gtdFragment = GTDFragment.newInstance();
        pomoFragment = PomodoroFragment.newInstance();
        tenMinFragment = TenMinuteFragment.newInstance();
        displayGTDFragment();
    }

    /**
     * Initializes user interface elements including {@link Toolbar},
     * {@link ActionBarDrawerToggle}, and {@link NavigationView}
     *
     * @author Yash
     */
    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = getDrawerToggle(toolbar);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    /**
     * @param toolbar {@link Toolbar} object
     * @return toggle to tie functionality of {@link DrawerLayout} and
     * {@link android.support.v7.app.ActionBar}
     * @see <a href="http://stackoverflow.com/questions/17515839/navigation-drawer-hide-keyboard-when-ondraweropened">
     * Stack Overflow - navigation drawer hide keyboard when onDrawerOpened</a>
     */
    private ActionBarDrawerToggle getDrawerToggle(final Toolbar toolbar) {
        return new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                // hides soft keyboard when nav drawer is opened
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
    }

    /**
     * Destroys the Notification Spawned from CountDownView
     *
     * @author Charles
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationUtil.destroyPomNotification(getApplicationContext());
    }


    /**
     * Displays the fragment depending on whether or not it exists.
     *
     * @author Charles
     * @see https://guides.codepath.com/android/Creating-and-Using-Fragments#managing-fragment-backstack
     */
    private void displayGTDFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (gtdFragment.isAdded()) {
            transaction.show(gtdFragment);
            setTitle("Task List");
        } else {
            transaction.add(R.id.content_nav, gtdFragment, FragmentTags.GTD_FRAGMENT);
        }
        if (pomoFragment.isAdded()) {
            transaction.hide(pomoFragment);
        }
        if (tenMinFragment.isAdded()) {
            transaction.hide(tenMinFragment);
        }
        transaction.commit();
    }

    /**
     * Displays the fragment depending on whether or not it exists.
     *
     * @author Charles
     */
    private void displayPomodoroFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (pomoFragment.isAdded()) {
            transaction.show(pomoFragment);
            setTitle("Pomodoro");
        } else {
            transaction.add(R.id.content_nav, pomoFragment, FragmentTags.POMO_FRAGMENT);
        }
        if (gtdFragment.isAdded()) {
            transaction.hide(gtdFragment);
        }
        if (tenMinFragment.isAdded()) {
            transaction.hide(tenMinFragment);
        }
        transaction.commit();
    }

    /**
     * Displays the fragment depending on whether or not it exists.
     *
     * @author Charles
     */
    private void displayTenMinFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (tenMinFragment.isAdded()) {
            transaction.show(tenMinFragment);
            setTitle("Ten Minute Hack");
        } else {
            transaction.add(R.id.content_nav, tenMinFragment, FragmentTags.TEN_MIN_FRAGMENT);
        }
        if (pomoFragment.isAdded()) {
            transaction.hide(pomoFragment);
        }
        if (gtdFragment.isAdded()) {
            transaction.hide(gtdFragment);
        }
        transaction.commit();
    }

    /**
     * Handles event of selecting a {@link NavigationView} item
     *
     * @param item {@link NavigationView} menu item selected
     * @return true if the event was handles, false otherwise
     * @author Charles
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        // determines appropriate fragment using item Id
        switch (item.getItemId()) {


            case R.id.menuitem_tasks:
                displayGTDFragment();
                break;
            case R.id.menuitem_pomodoro:

                displayPomodoroFragment();
                break;
            case R.id.menuitem_tenminute:
                displayTenMinFragment();
                break;
            default:
                displayGTDFragment();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Handles back button press
     * Case 1: {@link DrawerLayout} in current activity
     * Case 2: {@link DetailTaskFragment} to {@link GTDFragment}
     * Case 3: {@link PomodoroSettingsFragment} back button handler
     * Case 4: default handler
     *
     * @author Charles and Yash
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (getSupportFragmentManager().findFragmentById(R.id.content_nav)
                instanceof DetailTaskFragment) {
            displayGTDFragment();

        } else if (getSupportFragmentManager().findFragmentById(R.id.content_nav)
                instanceof PomodoroSettingsFragment) {
            PomodoroSettingsFragment psf = (PomodoroSettingsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.content_nav);
            psf.handleBackBtnPressed();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    /**
     * Handles touch motion event of the view
     *
     * @param event {@link MotionEvent} to be dispatched
     * @return true if touch event was handled
     * @author Yash
     * @see <a href="http://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext">
     * Stack Overflow - how to hide soft keyboard on android after clicking outside EditText?</a>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (getCurrentFocus() instanceof EditText) {
            int coords[] = new int[2];
            View w = getCurrentFocus();
            w.getLocationOnScreen(coords);

            float x = event.getRawX() + w.getLeft() - coords[0];
            float y = event.getRawY() + w.getTop() - coords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                hideKeyboard();
            }
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * Hides Android soft keyboard
     *
     * @author Yash
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
    }
}
