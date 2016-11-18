package finalproject.csci205.com.countdown;

/**
 * Created by ceh024 on 11/17/16.
 */

public interface CountDownListener {


    /**
     * Deliver's the result of the countdown to all views.
     * VIEWS MUST Implement this interface
     *
     * @param l
     */
    void countdownResult(long l);

    /**
     * Signals that the countdown is complete
     */
    void onCountFinished();
}
