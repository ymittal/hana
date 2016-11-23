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

import finalproject.csci205.com.countdown.View.CountDownView;
import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Pom.PomodoroSettings;
import finalproject.csci205.com.ymca.presenter.PomodoroPresenter;

/**
 * Created by ceh024 on 11/21/16.
 */

public class PomodoroSettingsFragment extends Fragment implements View.OnClickListener {

    CountDownView cdRef;
    private View root;
    private EditText sessionTime;
    private EditText breakTime;
    private EditText numBreaks;
    private EditText longBreak;
    private ImageButton saveBtn;
    private PomodoroPresenter pomodoroPresenter;
    private OnBackStackListener backStackListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.pomodorosettings_dialog, container, false);
        sessionTime = (EditText) root.findViewById(R.id.sessionTime);
        breakTime = (EditText) root.findViewById(R.id.breakTime);
        numBreaks = (EditText) root.findViewById(R.id.numBreaks);
        longBreak = (EditText) root.findViewById(R.id.longBreak);
        saveBtn = (ImageButton) root.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        pomodoroPresenter = new PomodoroPresenter(this);

        PomodoroSettings oldSettings = pomodoroPresenter.getSavedPomSettings();
        if (oldSettings != null) {
            sessionTime.setText(String.valueOf(oldSettings.getSessionTime()));
            breakTime.setText(String.valueOf(oldSettings.getNormBreakTime()));
            numBreaks.setText(String.valueOf(oldSettings.getNumCyclesTillBreak()));
            longBreak.setText(String.valueOf(oldSettings.getLongBreak()));
        }
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == saveBtn.getId()) {
            if (pomodoroPresenter.saveSettiings()) {
                backStackListener.onViewReturn();
                cdRef.setSessionTimeOverride(Integer.valueOf(sessionTime.getText().toString()));
                removeSelf();
            }

        }
    }

    public View getRoot() {
        return root;
    }


    /**
     * Auto pops this fragment allowing the user to return to the prior view.
     *
     * @author Charles
     */
    private void removeSelf() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        trans.remove(this).commit();
        manager.popBackStack();
    }

    /**
     * Defines the listener when the back / save button is pressed
     *
     * @param p
     * @author Charles
     */
    public void setOnBackStackListener(PomodoroFragment p) {
        backStackListener = p;
    }


    /**
     * Nav Activity Method that handles when user clicks back button
     *
     * @author Charles
     */
    public void handleBackBtnPressed() {
        backStackListener.onViewReturn();
        removeSelf();
    }

    /* Getters for presenter */
    public EditText getSessionTime() {
        return sessionTime;
    }

    public EditText getBreakTime() {
        return breakTime;
    }

    public EditText getNumBreaks() {
        return numBreaks;
    }

    public EditText getLongBreak() {
        return longBreak;
    }


    public void setCdRef(CountDownView cdRef) {
        this.cdRef = cdRef;
    }
}
