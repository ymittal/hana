package finalproject.csci205.com.ymca.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.view.task.DetailTaskFragment;
import finalproject.csci205.com.ymca.view.task.item.SubtasksAdapter;

/**
 * A class to encapsulate a presenter for the {@link DetailTaskFragment} following
 * MVP design pattern for Android development
 */
public class DetailTaskPresenter {

    /**
     * {@link SubtasksAdapter} object
     */
    private final SubtasksAdapter subtasksAdapter;
    /**
     * List of subtasks under given {@link Task}
     */
    private final List<Subtask> subtasks;
    /**
     * {@link DetailTaskFragment} fragment containing information about
     * a specific {@link Task}
     */
    private DetailTaskFragment fragment;

    /**
     * Default constructor
     */
    public DetailTaskPresenter() {
        this.subtasks = new ArrayList<>();
        this.subtasksAdapter = new SubtasksAdapter(this);
    }

    /**
     * Sets up presenter and initializes list of subtasks by querying database using {@link Task#id}
     *
     * @param fragment {@link DetailTaskFragment} object
     * @param task     {@link Task} object
     * @author Yash
     */
    public DetailTaskPresenter(DetailTaskFragment fragment, Task task) {
        this.fragment = fragment;
        this.subtasks = Subtask.find(Subtask.class, "task_id = ?", task.getId().toString());
        this.subtasksAdapter = new SubtasksAdapter(this);
    }

    /**
     * @return number of subtasks under {@link Task}
     * @author Yash
     */
    public int getNumSubtasks() {
        return subtasks.size();
    }

    /**
     * @return {@link SubtasksAdapter} object
     * @author Yash
     */
    public SubtasksAdapter getSubtasksAdapter() {
        return subtasksAdapter;
    }

    /**
     * @return list of subtasks
     * @author Yash
     */
    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    /**
     * Adds new {@link Subtask} to subtasks list
     *
     * @param newSubtask new {@link Subtask} object
     * @author Yash
     */
    public void addSubtask(Subtask newSubtask) {
        subtasks.add(newSubtask);
        newSubtask.save();
        subtasksAdapter.notifyDataSetChanged();
    }

    /**
     * Removes {@link Subtask} recyclerview item at given position
     *
     * @param pos position in list
     * @author Yash
     */
    public void removeSubtask(int pos) {
        Subtask subtaskToBeRemoved = subtasks.get(pos);
        subtaskToBeRemoved.delete();
        subtasks.remove(pos);
        subtasksAdapter.notifyItemRemoved(pos);
        subtasksAdapter.notifyItemRangeChanged(pos, getNumSubtasks());
    }

    /**
     * Sets due date of {@link Task} object
     *
     * @param task {@link Task} object
     * @param date task due date
     * @author Yash
     */
    public void setTaskDate(Task task, Date date) {
        task.setDueDate(date);
        task.save();
    }

    /**
     * Sets description of {@link Task} object
     *
     * @param task {@link Task} object
     * @param desc task description
     * @author Yash
     */
    public void setDescription(Task task, String desc) {
        task.setDesc(desc);
        task.save();
    }
}
