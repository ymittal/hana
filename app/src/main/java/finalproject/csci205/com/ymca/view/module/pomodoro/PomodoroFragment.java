package finalproject.csci205.com.ymca.view.module.pomodoro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import finalproject.csci205.com.countdown.Ults.ServiceState;
import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Pom.PomodoroSettings;
import finalproject.csci205.com.ymca.presenter.PomodoroPresenter;
import finalproject.csci205.com.ymca.util.Constants;
import finalproject.csci205.com.ymca.view.MainActivity;


public class PomodoroFragment extends Fragment implements View.OnClickListener, OnBackStackListener {

    /**
     * Default Pomodoro session period in minutes
     */
    public static final int DEFAULT_SESSION_TIME_IN_MINS = 25;
    /**
     * Default Pomodoro normal break period in minutes
     */
    public static final int DEFAULT_NORMAL_BREAK_IN_MINS = 5;
    /**
     * Default number of Pomodoro cycles
     */
    public static final int DEFAULT_NUM_CYCLES = 5;
    /**
     * Default Pomodoro long break period in minutes
     */
    public static final int DEFAULT_LONG_BREAK_IN_MINS = 60;

    /**
     * A {@link CountDownView} to display time left until next Pomodoro break
     */
    private CountDownView countDownView;
    /**
     * Presenter for {@link PomodoroFragment}
     */
    private PomodoroPresenter pomodoroPresenter;

    // User Interface elements for PomodoroFragment
    private ImageButton btnPomodoroSettings;

    /**
     * Required empty constructor
     */
    public PomodoroFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment {@link PomodoroFragment}.
     */
    public static PomodoroFragment newInstance() {
        PomodoroFragment fragment = new PomodoroFragment();
        return fragment;
    }

    /**
     * Sets up title of {@link PomodoroFragment}
     *
     * @param savedInstanceState
     * @author Charles
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Pomodoro");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pomodoro, container, false);

        pomodoroPresenter = new PomodoroPresenter(this);
        initUI(root);

        PomodoroSettings pomodoroSettings = pomodoroPresenter.getSavedPomSettings();
        if (pomodoroSettings != null) {
            countDownView.setSessionTime(pomodoroSettings.getSessionTime());
        } else {
            pomodoroPresenter.savePomodoroSettingsToDatabase(getDefaultPomodoroSettings());
            countDownView.setSessionTime(0); // temp config
        }

        countDownView.setJumpTo(MainActivity.class);

        return root;
    }

    /**
     * Initializes user interface elements and sets {@link android.view.View.OnClickListener}
     *
     * @param root root view for the fragment interface
     * @author Charles
     */
    private void initUI(View root) {
        countDownView = (CountDownView) root.findViewById(R.id.countDownViewInFragment);
        btnPomodoroSettings = (ImageButton) root.findViewById(R.id.btnPomodoroSettings);

        btnPomodoroSettings.setOnClickListener(this);
    }

    // TODO: write Javadocs (Charles)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownView.getCd().getState() == ServiceState.OTHER) {
            Constants.destroyPomNotification(getContext());
        }
    }

    /**
     * Handles click events on views implementing {@link android.view.View.OnClickListener}
     *
     * @param view clicked view
     * @author Charles
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPomodoroSettings) {
            btnPomodoroSettings.setVisibility(View.INVISIBLE);
            showSettings();
        }
    }

    /**
     * Overlays {@link PomodoroFragment} with an instance of
     * {@link PomodoroSettingsFragment} to gather Pomodoro settings
     *
     * @author Charles
     */
    private void showSettings() {
        PomodoroSettingsFragment settingsFragment = new PomodoroSettingsFragment();
        settingsFragment.setOnBackStackListener(this);
        settingsFragment.setCountDownView(countDownView);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.content_nav, settingsFragment).addToBackStack(null).commit();
    }

    /**
     * Sets visibility of Pomodoro settings button to {@link View#VISIBLE}
     *
     * @author Charles
     */
    @Override
    public void onViewReturn() {
        btnPomodoroSettings.setVisibility(View.VISIBLE);
    }

    /**
     * @return default {@link PomodoroSettings} configuration for the Pomodoro module
     * of the application
     */
    public PomodoroSettings getDefaultPomodoroSettings() {
        PomodoroSettings ps = new PomodoroSettings();
        ps.setSessionTime(DEFAULT_SESSION_TIME_IN_MINS);
        ps.setNormBreakTime(DEFAULT_NORMAL_BREAK_IN_MINS);
        ps.setNumCyclesTillBreak(DEFAULT_NUM_CYCLES);
        ps.setLongBreak(DEFAULT_LONG_BREAK_IN_MINS);
        return ps;
    }
}
