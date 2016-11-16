package finalproject.csci205.com.ymca.presenter.module;

import android.support.v4.app.Fragment;

import finalproject.csci205.com.ymca.view.module.Pomodoro.PomodoroFragment;

/**
 * Created by ceh024 on 11/6/16.
 */

public class PomodoroPresenter implements Presenter {

    private PomodoroFragment view;

    @Override
    public void setView(Fragment f) {
        this.view = (PomodoroFragment) f;
    }
}
