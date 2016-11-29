package finalproject.csci205.com.ymca.view;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import finalproject.csci205.com.ymca.util.Constants;
import finalproject.csci205.com.ymca.view.module.pomodoro.PomodoroFragment;
import finalproject.csci205.com.ymca.view.module.pomodoro.PomodoroSettingsFragment;
import finalproject.csci205.com.ymca.view.task.DetailTaskFragment;
import finalproject.csci205.com.ymca.view.task.GTDFragment;

/**
 * {@link android.app.Activity} to hold the hamburger menu allowing user to
 * switch between different productivity techniques
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
     * Sets up activity user interface and controls
     *
     * @param savedInstanceState
     * @author
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        initUI();
        initFragment(new GTDFragment());
    }

    /**
     * Initializes user interface elements including {@link Toolbar},
     * {@link ActionBarDrawerToggle}, and {@link NavigationView}
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
        Constants.destroyPomNotification(getApplicationContext());
    }

    /**
     * Initializes fragment by changing container view content
     *
     * @param newFragment new {@link Fragment} object
     */
    private void initFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_nav, newFragment);
        transaction.commit();
    }

    /**
     * Handles event of selecting a {@link NavigationView} item
     *
     * @param item {@link NavigationView} menu item selected
     * @return true if the event was handles, false otherwise
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;

        // determines appropriate fragment using item Id
        switch (item.getItemId()) {
            case R.id.GTD_tasks:
                fragmentClass = GTDFragment.class;
                break;
            case R.id.Pom:
                fragmentClass = PomodoroFragment.class;
                break;
            default:
                fragmentClass = GTDFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFragment(fragment);

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
            initFragment(new GTDFragment());

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
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
    }
}
