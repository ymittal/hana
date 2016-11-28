package finalproject.csci205.com.ymca.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * A class to represent a task, extending from {@link SugarRecord} to save it to local
 * database
 *
 * @author Aleks, Malachi, and Yash
 */
public class Task extends SugarRecord implements Serializable {

    /**
     * Task title
     */
    private String title;
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
}
