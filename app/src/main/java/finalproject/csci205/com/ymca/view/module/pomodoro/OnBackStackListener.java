package finalproject.csci205.com.ymca.view.module.pomodoro;


/**
 * Custom listener for when user navigates away from
 * {@link finalproject.csci205.com.ymca.model.PomodoroSettings} back to the
 * {@link PomodoroFragment}
 *
 * @author Charles
 */
interface OnBackStackListener {

    /**
     * Function evoked when user clicks Android back button
     */
    void onViewReturn();
}
