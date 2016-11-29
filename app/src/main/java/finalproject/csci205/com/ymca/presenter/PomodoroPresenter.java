package finalproject.csci205.com.ymca.presenter;

import android.content.Context;
import android.widget.Toast;

import finalproject.csci205.com.countdown.Service.CountDownListener;
import finalproject.csci205.com.ymca.model.Pom.PomodoroSettings;
import finalproject.csci205.com.ymca.view.module.pomodoro.CountDownView;
import finalproject.csci205.com.ymca.view.module.pomodoro.PomodoroFragment;
import finalproject.csci205.com.ymca.view.module.pomodoro.PomodoroSettingsFragment;

/**
 * Created by ceh024 on 11/6/16.
 */

public class PomodoroPresenter implements CountDownListener {

    private final long DB_ID = 4l;
    private CountDownView cdView;
    private PomodoroFragment view;
    private PomodoroSettingsFragment settingsView;
    private PomodoroSettings pomSettings;
    private Context context;
    private int sessionTime;
    private int breakTime;
    private int numBreaks;
    private int longBreak;

    private int cycleCounter;


    public PomodoroPresenter(PomodoroFragment p) {
        view = p;
        this.context = p.getContext();
    }

    public PomodoroPresenter(PomodoroSettingsFragment q) {
        settingsView = q;
        this.context = q.getContext();
    }

    /**
     * Stores all settings to model
     *
     * @author Charles
     */
    public boolean saveSettiings() {
        try {
            sessionTime = Integer.valueOf(settingsView.getSessionTime().getText().toString());
            breakTime = Integer.valueOf(settingsView.getBreakTime().getText().toString());
            numBreaks = Integer.valueOf(settingsView.getNumBreaks().getText().toString());
            longBreak = Integer.valueOf(settingsView.getLongBreak().getText().toString());
            pomSettings = getSavedPomSettings();
            if (pomSettings == null) {
                pomSettings = new PomodoroSettings(sessionTime, breakTime, numBreaks, longBreak);
                pomSettings.setId(DB_ID);
                pomSettings.save();
            } else {
                pomSettings.setSessionTime(sessionTime);
                pomSettings.setNormBreakTime(breakTime);
                pomSettings.setNumCyclesTillBreak(numBreaks);
                pomSettings.setLongBreak(longBreak);
                pomSettings.save();
            }
            return true;
        } catch (NumberFormatException ex) {
            Toast.makeText(settingsView.getContext(), "Invalid input paramater, try again", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * Restores saved {@link PomodoroSettings} from local database
     *
     * @author Charles
     */
    public PomodoroSettings getSavedPomSettings() {
        return PomodoroSettings.findById(PomodoroSettings.class, DB_ID);
    }

    /**
     * Updates Session time
     *
     * @param update
     * @author Charles
     */
    public void setSessionUpdate(int update) {
        cdView.setSessionTime(update);
    }

    public void setCountDownView(CountDownView cdView) {
        this.cdView = cdView;
    }

    public void incCounter() {
        cycleCounter++;
    }

    @Override
    public void countdownResult(long l) {

    }

    @Override
    public void onCountFinished() {
        incCounter();
    }
}
