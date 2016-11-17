package finalproject.csci205.com.ymca.presenter.module;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.GTDPresenterInterface;
import finalproject.csci205.com.ymca.view.task.DetailTaskFragment;
import finalproject.csci205.com.ymca.view.task.GTDFragment;
import finalproject.csci205.com.ymca.view.task.dialog.AddQuickTaskDialog;
import finalproject.csci205.com.ymca.view.task.item.TasksAdapter;

/**
 * Created by ceh024 on 11/6/16.
 */

public class GTDPresenter implements GTDPresenterInterface {

    public static final String SERIALIZED_TASK = "SERIALIZED_TASK";

    private GTDFragment view;
    private TasksAdapter tasksAdapter;
    private List<Task> tasks;
    private Task dismissedTask;


    /**
     * Standard Constructor scheme used for Presenter-->Fragment
     *
     * @param view
     * @author Charles
     */
    public GTDPresenter(GTDFragment view) {
        this.view = view;
        this.tasks = new ArrayList<>();
        this.tasks = Task.listAll(Task.class);
        this.tasksAdapter = new TasksAdapter(this);
    }

    /**
     * Special Case Constructor for model access, used for TaskFormFragment.
     *
     * @author Charles
     */
    public GTDPresenter() {
        this.tasks = new ArrayList<>();
        this.tasks = Task.listAll(Task.class);
    }


    @Override
    public void createQuickTask() {

    }

    @Override
    public void createGTDTask() {

    }

    public TasksAdapter getTasksAdapter() {
        return tasksAdapter;
    }


    /*
    See video for awsome sauce.
    Note this code is example code
     */
    public void dialogTest() {
        AddQuickTaskDialog dialog = new AddQuickTaskDialog();
        dialog.setTargetFragment(view, 1);
        dialog.show(view.getFragmentManager(), "Add Task");
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int taskSize() {
        return tasks.size();
    }

    /**
     * Add's task to model, updates view
     *
     * @param t
     * @param b checks to see if we are adjusting the view. Relies on which constructor
     *          was used. Expects programmer to be aware of which class depends on each case.
     *          GTDFragment, b = true. Otherwise it is false.
     * @author Charles & Yash
     */
    public void addTask(Task t, boolean b) {
        getTasks().add(t);
        t.save();
        if (b) {
            tasksAdapter.notifyDataSetChanged();
        }

    }

    /**
     * Removes task from model, updates view.
     * Stores old task if user wish's to immediately restore it.
     *
     * @param index
     * @author Charles & Yash
     */
    public void removeTask(int index) {
        Task todoToBeRemoved = getTasks().get(index);
        dismissedTask = todoToBeRemoved;
        todoToBeRemoved.delete();
        getTasks().remove(index);
        tasksAdapter.notifyItemRemoved(index);
    }

    /**
     * Restore's Task if user so chooses
     *
     * @param index
     * @author Charles and Yash
     */
    public void restoreTask(int index) {
        dismissedTask.save();
        getTasks().add(index, dismissedTask);
        tasksAdapter.notifyItemInserted(index);
    }

    /**
     * Changes the state of the Task in the model, updates the model.
     *
     * @param index
     * @param b
     * @author Charles and Yash
     */
    public void taskChecked(int index, boolean b) {
        getTasks().get(index).setIsComplete(b);
        getTasks().get(index).save();
    }

    public void showGTD() {
        view.getView().setVisibility(View.VISIBLE);
    }

    public void openDetailedTaskFragment(Task task) {
        DetailTaskFragment detailTaskFragment = new DetailTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(SERIALIZED_TASK, task);
        detailTaskFragment.setArguments(args);

        FragmentManager fragmentManager = view.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.content_nav, detailTaskFragment).commit();
    }
}
