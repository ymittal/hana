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

    private CountDownView countDownView;
    private ImageButton btnPomodoroSettings;
    private PomodoroPresenter pomodoroPresenter;

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
        pomodoroPresenter = new PomodoroPresenter(this);
        PomodoroSettings pomodoroSettings = pomodoroPresenter.getSavedPomSettings();
        View root = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        countDownView = (CountDownView) root.findViewById(R.id.countDownViewInFragment);

        if (pomodoroSettings == null) {
            pomodoroPresenter.savePomodoroSettings(getDefaultPomodoroSettings());
        }
        countDownView.setSessionTime(pomodoroSettings.getSessionTime());

        countDownView.setJumpTo(MainActivity.class);
        btnPomodoroSettings = (ImageButton) root.findViewById(R.id.btnPomodoroSettings);
        btnPomodoroSettings.setOnClickListener(this);


        return root;
    }

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
            btnPomodoroSettings.setVisibility(View.GONE);
            showSettings();
        }
    }

    /**
     * Overlays {@link PomodoroSettingsFragment} to gather Pomodoro settings
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

    public PomodoroSettings getDefaultPomodoroSettings() {
        // TODO: extract constants
        PomodoroSettings ps = new PomodoroSettings();
        ps.setSessionTime(25);
        ps.setNormBreakTime(5);
        ps.setNumCyclesTillBreak(5);
        ps.setLongBreak(60);
        return ps;
    }
}
