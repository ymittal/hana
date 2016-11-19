package finalproject.csci205.com.ymca.presenter.module;

import java.util.List;

import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.view.task.DetailTaskFragment;
import finalproject.csci205.com.ymca.view.task.item.SubtasksAdapter;

/**
 * Created by ym012 on 11/18/2016.
 */

public class DetailTaskPresenter {

    private DetailTaskFragment view;
    private SubtasksAdapter subtasksAdapter;
    private List<Subtask> subtasks;

    public DetailTaskPresenter() {
    }

    public DetailTaskPresenter(DetailTaskFragment view, Task task) {
        this.view = view;
        this.subtasks = Subtask.find(Subtask.class, "task_id = ?", task.getId().toString());
        this.subtasks.add(new Subtask(task.getId(), "Subtask 1"));
        this.subtasks.add(new Subtask(task.getId(), "Subtask 2"));
        this.subtasks.add(new Subtask(task.getId(), "Subtask 3"));
        this.subtasks.add(new Subtask(task.getId(), "Subtask 4"));
        this.subtasksAdapter = new SubtasksAdapter(this);
    }

    public int getNumSubtasks() {
        return subtasks.size();
    }

    public SubtasksAdapter getSubtasksAdapter() {
        return subtasksAdapter;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }
}
