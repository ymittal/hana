package finalproject.csci205.com.ymca.view.module.pomodoro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.PomodoroSettings;
import finalproject.csci205.com.ymca.presenter.PomodoroPresenter;

/**
 * A fragment to encapsulate the different settings related to the
 * Pomodoro technique, storing them through the presenter.
 *
 * @author Charles
 */
public class PomodoroSettingsFragment extends Fragment implements View.OnClickListener {

    /**
     * Model, View, Presenter and listener refrences.
     */
    private CountDownView cdRef;
    private PomodoroPresenter pomodoroPresenter;
    private OnBackStackListener backStackListener;

    /**
     * UI elements
     */
    private EditText sessionTime;
    private EditText breakTime;
    private EditText numBreaks;
    private EditText longBreak;
    private ImageButton saveBtn;

    /**
     * Sets up fragment user interface and sets current Pomodoro settings
     *
     * @param inflater           {@link LayoutInflater} to inflate views inside fragment
     * @param container          parent view encapsulating the fragment
     * @param savedInstanceState
     * @return view for the fragment interface
     * @author Yash and Charles
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pomodoro_settings, container, false);

        pomodoroPresenter = new PomodoroPresenter(this);
        initUI(root);
        setCurrentPomodoroSettings();

        return root;
    }

    /**
     * Initializes user interface elements and sets {@link android.view.View.OnClickListener}
     *
     * @param root root view for the fragment interface
     * @author Charles
     */
    private void initUI(View root) {
        sessionTime = (EditText) root.findViewById(R.id.sessionTime);
        breakTime = (EditText) root.findViewById(R.id.breakTime);
        numBreaks = (EditText) root.findViewById(R.id.numBreaks);
        longBreak = (EditText) root.findViewById(R.id.longBreak);
        saveBtn = (ImageButton) root.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(this);
    }

    /**
     * Sets the current Pomodoro settings in the appropriate EditTexts
     *
     * @author Charles
     */
    private void setCurrentPomodoroSettings() {
        PomodoroSettings oldSettings = pomodoroPresenter.getSavedPomSettings();
        if (oldSettings != null) {
            sessionTime.setText(String.valueOf(oldSettings.getSessionTime()));
            breakTime.setText(String.valueOf(oldSettings.getNormBreakTime()));
            numBreaks.setText(String.valueOf(oldSettings.getNumCyclesTillBreak()));
            longBreak.setText(String.valueOf(oldSettings.getLongBreak()));
        }
    }

    /**
     * @return currently configured Pomodoro settings
     * @author Charles and Yash
     */
    private PomodoroSettings getCurrentPomodoroSettings() {
        try {
            PomodoroSettings ps = new PomodoroSettings();
            ps.setSessionTime(Integer.valueOf(sessionTime.getText().toString()));
            ps.setNormBreakTime(Integer.valueOf(breakTime.getText().toString()));
            ps.setNumCyclesTillBreak(Integer.valueOf(numBreaks.getText().toString()));
            ps.setLongBreak(Integer.valueOf(longBreak.getText().toString()));
            return ps;

        } catch (NumberFormatException ex) {
            Toast.makeText(getContext(), R.string.err_pom_settings_invalid, Toast.LENGTH_SHORT).show();
            return null;
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
        if (view.getId() == saveBtn.getId()) {
            PomodoroSettings ps = getCurrentPomodoroSettings();

            // saves current Pomodoro settings to database
            // alerts BackStackListener in PomodoroFragment
            // updates CountDownView references to internal Pomodoro data
            // flow: model --> presenter --> CountDownView
            if (ps != null) {
                pomodoroPresenter.savePomodoroSettingsToDatabase(ps);
                backStackListener.onViewReturn();
                cdRef.newSavedConfig();
                removeSelf();
            }
        }
    }

    /**
     * Auto pops this fragment allowing the user to return to the prior view.
     *
     * @author Charles
     */
    private void removeSelf() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(this).commit();
        manager.popBackStack();
    }

    /**
     * Defines {@link OnBackStackListener} when the back or save button is pressed
     *
     * @param p {@link PomodoroFragment} instance
     * @author Charles
     */
    public void setOnBackStackListener(PomodoroFragment p) {
        backStackListener = p;
    }


    /**
     * {@link finalproject.csci205.com.ymca.view.NavActivity} method to handle when user
     * clicks back button
     *
     * @author Charles
     */
    public void handleBackBtnPressed() {
        backStackListener.onViewReturn();
        removeSelf();
    }

    /**
     * Sets {@link CountDownView} for the fragment
     *
     * @param cdRef {@link CountDownView} instance
     * @author Charles
     */
    public void setCountDownView(CountDownView cdRef) {
        this.cdRef = cdRef;
    }
}