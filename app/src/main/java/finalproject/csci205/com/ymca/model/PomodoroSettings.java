package finalproject.csci205.com.ymca.model;

import com.orm.SugarRecord;

/**
 * A class to represent different settings regarding Pomodoro technique,
 * extending from {@link SugarRecord} to save it to local database
 */
public class PomodoroSettings extends SugarRecord {

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
    public static final int DEFAULT_LONG_BREAK_IN_MINS = 15;

    /**
     * Time for standard work period. Should be 30-60 min
     */
    private int sessionTime;
    /**
     * Default time for break, usually 5 min but can be up to 10
     */
    private int normBreakTime;
    /**
     * Time for long break, usually 10-15 min but can be longer
     */
    private int longBreak;
    /**
     * Cycles in between short/long breaks
     */
    private int numCyclesTillBreak;

    /**
     * Constructs a {@link PomodoroSettings} object with the given fields
     *
     * @param sessionTime
     * @param normBreakTime
     * @param longBreak
     * @param numCyclesTillBreak
     */
    public PomodoroSettings(int sessionTime, int normBreakTime, int longBreak, int numCyclesTillBreak) {
        this.sessionTime = sessionTime;
        this.normBreakTime = normBreakTime;
        this.longBreak = longBreak;
        this.numCyclesTillBreak = numCyclesTillBreak;
    }

    /**
     * Required empty constructor
     */
    public PomodoroSettings() {
    }

    public int getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    public int getNormBreakTime() {
        return normBreakTime;
    }

    public void setNormBreakTime(int normBreakTime) {
        this.normBreakTime = normBreakTime;
    }

    public int getLongBreak() {
        return longBreak;
    }

    public void setLongBreak(int longBreak) {
        this.longBreak = longBreak;
    }

    public int getNumCyclesTillBreak() {
        return numCyclesTillBreak;
    }

    public void setNumCyclesTillBreak(int numCyclesTillBreak) {
        this.numCyclesTillBreak = numCyclesTillBreak;
    }
}
