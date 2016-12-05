package finalproject.csci205.com.ymca.view.module.pomodoro;

/**
 * An Interface to sync view and notification
 *
 * @author Charles
 */
interface NotificationClickedSyncListener {

    /**
     * Handler when start button is clicked
     */
    void onStartClicked();

    /**
     * Handler when pause button is clicked
     */
    void onPausedClicked();

    /**
     * Handler when stop button is clicked
     */
    void onStopClicked();

}
