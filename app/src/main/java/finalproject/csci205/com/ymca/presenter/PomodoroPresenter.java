package finalproject.csci205.com.ymca.presenter;

import android.content.Context;

import finalproject.csci205.com.countdown.Service.CountDownListener;
import finalproject.csci205.com.ymca.model.PomodoroSettings;
import finalproject.csci205.com.ymca.view.module.pomodoro.CountDownView;
import finalproject.csci205.com.ymca.view.module.pomodoro.PomodoroFragment;
import finalproject.csci205.com.ymca.view.module.pomodoro.PomodoroSettingsFragment;

/**
 * A class to encapsulate a presenter for the {@link PomodoroFragment},
 * {@link PomodoroSettingsFragment}, and {@link CountDownView} following MVP
 * design pattern for Android development
 * @author Charles
 */
public class PomodoroPresenter implements CountDownListener {

    private final static long DB_ID = 4L;

    private static PomodoroSettings pomodoroSettings;
    private PomodoroFragment view;
    private PomodoroSettingsFragment settingsView;
    private Context context;
    private int cycleCounter;

    /**
     * Default constructor
     */
    public PomodoroPresenter() {
    }

    /**
     * Sets up presenter for {@link PomodoroFragment} fragment
     *
     * @param pomodoroFrag {@link PomodoroFragment} object
     * @author Charles
     */
    public PomodoroPresenter(PomodoroFragment pomodoroFrag) {
        this.view = pomodoroFrag;
        this.context = pomodoroFrag.getContext();
    }

    /**
     * Sets up presenter for {@link PomodoroSettingsFragment} fragment
     *
     * @param pomodoroSettingsFram {@link PomodoroSettingsFragment} object
     * @author Charles
     */
    public PomodoroPresenter(PomodoroSettingsFragment pomodoroSettingsFram) {
        settingsView = pomodoroSettingsFram;
        this.context = pomodoroSettingsFram.getContext();
    }

    /**
     * Restores saved {@link PomodoroSettings} from local database using {@link #DB_ID}
     *
     * @author Charles
     */
    public PomodoroSettings getSavedPomSettings() {
        return PomodoroSettings.findById(PomodoroSettings.class, DB_ID);
    }

    private void incCounter() {
        cycleCounter++;
    }

    @Override
    public void countdownResult(long l) {
    }

    @Override
    public void onCountFinished() {
        incCounter();
    }

    /**
     * Saves latest {@link PomodoroSettings} to local database
     *
     * @param ps new {@link PomodoroSettings} object
     * @author Yash
     */
    public void savePomodoroSettingsToDatabase(PomodoroSettings ps) {
        pomodoroSettings = ps;
        pomodoroSettings.setId(DB_ID);
        ps.save();
    }

}
