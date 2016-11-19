package finalproject.csci205.com.ymca.model;

import com.orm.SugarRecord;

/**
 * Created by ym012 on 11/18/2016.
 */

public class Subtask extends SugarRecord {

    private Long taskId;
    private String title;
    private boolean isComplete;

    public Subtask() {
    }

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
