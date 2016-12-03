package finalproject.csci205.com.ymca.model;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * A class to represent a task, extending from {@link SugarRecord} to save it to local
 * database
 *
 * @author Aleks, Malachi, and Yash
 */
public class Task extends SugarRecord implements Serializable, Comparable {

    /**
     * Task title
     */
    private final String title;
    /**
     * Description of task
     */
    private String desc;
    /**
     * Task completion status
     */
    private Boolean isComplete;
    /**
     * Task due date
     */
    private Date dueDate;

    /**
     * Empty constructor
     */
    public Task() {
        this.title = "";
        this.desc = "";
        this.isComplete = false;
        this.dueDate = null;
    }

    /**
     * Constructs a {@link Task} object with title
     *
     * @param title task title
     */
    public Task(String title) {
        this.title = title;
        this.desc = "";
        this.isComplete = false;
        this.dueDate = null;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean isComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Method used to sort a list of {@link Task} objects by priority.
     * Existing priority criterion is as follows:
     * If either of the two tasks is complete, the completed task is greater than the other.
     * Otherwise, the task having an earlier due date is considered smaller.
     *
     * @param obj {@link Task} object to compare current object with
     * @return a negative, zero, or a positive value denoting whether the current object
     * is less than, equal to, or greater than the passed object
     */
    @Override
    public int compareTo(@NonNull Object obj) {
        Task task2 = (Task) obj;
        if ((isComplete() && task2.isComplete()) || !(isComplete() || task2.isComplete())) {
            if (getDueDate() == null) {
                return -1;
            } else if (task2.getDueDate() == null) {
                return 1;
            } else {
                return getDueDate().compareTo(task2.getDueDate());
            }
        } else {
            return isComplete() ? 1 : -1;
        }
    }
}
