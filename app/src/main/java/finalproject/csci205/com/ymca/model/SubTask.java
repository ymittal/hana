package finalproject.csci205.com.ymca.model;

import com.orm.SugarRecord;

/**
 * A class to represent a subtask, extending from {@link SugarRecord} to save it to
 * local database
 *
 * @author Malachi and Yash
 */
public class Subtask extends SugarRecord {

    /**
     * Id of {@link Task} under which {@link Subtask} is present
     */
    private Long taskId;
    /**
     * Title of subtask
     */
    private String title;
    /**
     * Subtask completion status
     */
    private boolean isComplete;

    /**
     * Required empty constructor
     */
    public Subtask() {
    }

    /**
     * Constructs a {@link Subtask} object using {@link Task} id and subtask title
     *
     * @param taskId  Id of {@link Task} under which {@link Subtask} is present
     * @param subtask title of subtask
     */
    public Subtask(Long taskId, String subtask) {
        this.taskId = taskId;
        this.title = subtask;
        this.isComplete = false;
    }

    public String getTitle() {
        return title;
    }

    public void setIsComplete(boolean complete) {
        isComplete = complete;
    }
}
