package finalproject.csci205.com.ymca.model.Pom;

/**
 * Created by ceh024 on 11/16/16.
 * Collection of settings
 */

public class PomBundle {
    private int sessionTime;
    private int normBreakTime;
    private int longBreak;
    private int numCyclesTillBreak;


    public PomBundle(int sessionTime, int normBreakTime, int longBreak, int numCyclesTillBreak) {
        this.sessionTime = sessionTime;
        this.normBreakTime = normBreakTime;
        this.longBreak = longBreak;
        this.numCyclesTillBreak = numCyclesTillBreak;
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
