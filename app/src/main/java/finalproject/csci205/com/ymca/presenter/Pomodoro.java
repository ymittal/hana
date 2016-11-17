package finalproject.csci205.com.ymca.presenter;

import finalproject.csci205.com.ymca.model.Pom.PomBundle;

/**
 * Created by ceh024 on 11/16/16.
 */

public interface Pomodoro {
    /**
     * Connects view to background service to keep them in sync
     *
     * @author Charles
     */
    void bindService();

    /**
     * Displays notification for user control of pomorodo
     *
     * @author Charles
     */
    void toggleNotifiation();

    /**
     * Starts CountDownListener
     *
     * @author Charles
     */
    void startCountDown();

    /**
     * Pauses CountDownListener
     *
     * @author Charles
     */
    void pauseCountDown();

    /**
     * Terminates countdown, destroys service and notification
     *
     * @author Charles
     */
    void stopCountDown();

    /**
     * Saves user defined settings to Sugar ORM Model
     *
     * @param p Settings bundle;
     * @author Charles
     */
    void saveSettings(PomBundle p);
}
