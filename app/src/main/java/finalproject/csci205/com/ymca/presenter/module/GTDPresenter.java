package finalproject.csci205.com.ymca.presenter.module;

import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.model.item.TasksAdapter;
import finalproject.csci205.com.ymca.presenter.GTDPresenterInterface;
import finalproject.csci205.com.ymca.view.dialog.QuickTaskDialogFragment;
import finalproject.csci205.com.ymca.view.module.GTDFragment;

/**
 * Created by ceh024 on 11/6/16.
 */

public class GTDPresenter implements GTDPresenterInterface {


    private GTDFragment view;


    private TasksAdapter tasksAdapter;

    public GTDPresenter(GTDFragment view) {
        this.view = view;
        this.tasksAdapter = new TasksAdapter();
    }

//    @Override
//    public void setView(Fragment f) {
//        this.view = (GTDFragment) f;
//    }

    @Override
    public void createQuickTask() {

    }

    @Override
    public void createGTDTask() {

    }

    public TasksAdapter getTasksAdapter() {
        return tasksAdapter;
    }

    public void addItem(Task t) {
        tasksAdapter.addItem(t);
    }

    /*
    See video for awsome sauce.
     */
    public void dialogTest() {
        QuickTaskDialogFragment dialog = new QuickTaskDialogFragment();
        dialog.setTargetFragment(view, 1);
        dialog.show(view.getFragmentManager(), "Add Task");
    }
}
