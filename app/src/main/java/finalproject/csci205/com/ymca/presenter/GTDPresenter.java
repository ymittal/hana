package finalproject.csci205.com.ymca.presenter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.view.task.DetailTaskFragment;
import finalproject.csci205.com.ymca.view.task.GTDFragment;
import finalproject.csci205.com.ymca.view.task.item.TasksAdapter;

/**
 * A class to encapsulate a presenter for the {@link GTDFragment} following
 * MVP design pattern for Android development
 * @author Charles and Yash
 */
public class GTDPresenter {

    /**
     * Constant for {@link Task} serializable passed when an instance of
     * {@link DetailTaskFragment} is launched
     */
    private static final String SERIALIZED_TASK = "SERIALIZED_TASK";
    /**
     * {@link TasksAdapter} object
     */
    private final TasksAdapter tasksAdapter;
    /**
     * List of tasks added by user
     */
    private final List<Task> tasks;
    /**
     * {@link GTDFragment} object
     */
    private GTDFragment view;
    /**
     * A {@link Task} object removed by user but currently in contention of
     * getting restored through {@link android.support.design.widget.Snackbar} message
     */
    private Task dismissedTask;

    /**
     * Default constructor; for performing unit testing.
     *
     * @param ctx view {@link Context}
     * @author Charles
     */
    public GTDPresenter(Context ctx) {
        this.tasks = Task.listAll(Task.class);
        Collections.sort(this.tasks);
        this.tasksAdapter = new TasksAdapter(this, ctx);
    }


    /**
     * Sets up presenter and initializes list of tasks by querying database
     *
     * @param view {@link GTDFragment} object
     * @author Charles
     */
    public GTDPresenter(GTDFragment view) {
        this.view = view;
        this.tasks = Task.listAll(Task.class);
        Collections.sort(this.tasks);
        this.tasksAdapter = new TasksAdapter(this, view.getContext());
    }

    /**
     * @return {@link TasksAdapter} object
     */
    public TasksAdapter getTasksAdapter() {
        return tasksAdapter;
    }

    /**
     * @return list of tasks
     * @author Charles
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * @return nunber of {@link Task} object in list
     * @author Charles
     */
    public int taskSize() {
        return tasks.size();
    }

    /**
     * Adds {@link Task} object to database, updates view
     *
     * @param t new {@link Task} object to be stored
     * @param b checks to see if we are adjusting the view, relies on which constructor
     *          was used
     * @author Charles & Yash
     */
    public void addTask(Task t, boolean b) {
        tasks.add(0, t);    // adds task as the first list item
        t.save();
        if (b) {
            tasksAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Removes {@link Task} object from database, updates view.
     * Stores old task if user wishes to restore it immediately
     *
     * @param index position in task list
     * @author Charles & Yash
     */
    public void removeTask(int index) {
        Task todoToBeRemoved = tasks.get(index);
        dismissedTask = todoToBeRemoved;
        todoToBeRemoved.delete();
        tasks.remove(index);
        tasksAdapter.notifyItemRemoved(index);
    }

    /**
     * Restores {@link Task} object if user so chooses
     *
     * @param index position in task list
     * @author Charles and Yash
     */
    public void restoreTask(int index) {
        dismissedTask.save();
        tasks.add(index, dismissedTask);
        tasksAdapter.notifyItemInserted(index);
        tasksAdapter.notifyDataSetChanged();
    }

    /**
     * Changes task completion status for {@link Task} in database
     *
     * @param index    position of {@link Task} object in list
     * @param changeTo new completion status
     * @author Charles and Yash
     */
    public void taskChecked(int index, boolean changeTo) {
        tasks.get(index).setIsComplete(changeTo);
        tasks.get(index).save();
    }

    /**
     * Launches {@link DetailTaskFragment} for when a specific {@link Task} is
     * tapped on {@link android.support.v7.widget.RecyclerView}
     *
     * @param task {@link Task} object tapped
     */
    public void openDetailedTaskFragment(Task task) {
        DetailTaskFragment detailTaskFragment = new DetailTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(SERIALIZED_TASK, task);
        detailTaskFragment.setArguments(args);

        FragmentManager fragmentManager = view.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_nav, detailTaskFragment).commit();
    }
}
