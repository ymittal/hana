package finalproject.csci205.com.ymca.presenter.module;

import java.util.ArrayList;
import java.util.List;

import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.GTDPresenterInterface;
import finalproject.csci205.com.ymca.view.dialog.QuickTaskDialog;
import finalproject.csci205.com.ymca.view.module.gtd.GTDFragment;
import finalproject.csci205.com.ymca.view.module.gtd.item.TasksAdapter;

/**
 * Created by ceh024 on 11/6/16.
 */

public class GTDPresenter implements GTDPresenterInterface {


    private GTDFragment view;
    private TasksAdapter tasksAdapter;
    private List<Task> tasks;
    private Task dismissedTask;



    public GTDPresenter(GTDFragment view) {
        this.view = view;
        this.tasks = new ArrayList<>();
        this.tasks = Task.listAll(Task.class); //Ask Yash what this does.
        this.tasksAdapter = new TasksAdapter(this);

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

//    public void addItem(Task t) {
//        tasksAdapter.addItem(t);
//    }

    /*
    See video for awsome sauce.
    Note this code is example code
     */
    public void dialogTest() {
        QuickTaskDialog dialog = new QuickTaskDialog();
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
     * @author Charles & Yash
     */
    public void addTask(Task t) {
        getTasks().add(t);
        t.save();
        tasksAdapter.notifyDataSetChanged();
    }

    /**
     * Removes task from model, updates view.
     * Stores old task if user wish's to immediately restore it.
     * @author Charles & Yash
     * @param index
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
     * @author Charles and Yash
     * @param index
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

}
