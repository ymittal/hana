package finalproject.csci205.com.ymca.presenter.module;

import java.util.ArrayList;
import java.util.List;

import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.GTDPresenterInterface;
import finalproject.csci205.com.ymca.view.dialog.QuickTaskDialogFragment;
import finalproject.csci205.com.ymca.view.module.GTD.GTDFragment;
import finalproject.csci205.com.ymca.view.module.GTD.item.TasksAdapter;

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
     */
    public void dialogTest() {
        QuickTaskDialogFragment dialog = new QuickTaskDialogFragment();
        dialog.setTargetFragment(view, 1);
        dialog.show(view.getFragmentManager(), "Add Task");
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int taskSize() {
        return tasks.size();
    }

    public void addTask(Task t) {
        getTasks().add(t);
        t.save();
        tasksAdapter.notifyDataSetChanged();
    }

    public void removeTask(int index) {
        Task todoToBeRemoved = getTasks().get(index);
        dismissedTask = todoToBeRemoved;
        todoToBeRemoved.delete();
        getTasks().remove(index);
        tasksAdapter.notifyItemRemoved(index);
    }

    public void restoreTask(int index) {
        dismissedTask.save();
        getTasks().add(index, dismissedTask);
        tasksAdapter.notifyItemInserted(index);
    }

}
