package finalproject.csci205.com.ymca.presenter;

/**
 * Created by ceh024 on 11/6/16.
 * Defines common lifecyle methods.
 */

public interface LifeCycle {

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}