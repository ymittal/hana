package finalproject.csci205.com.ymca.model;

import com.orm.SugarRecord;

/**
 * Created by ym012 on 11/18/2016.
 */

public class SubTask extends SugarRecord {

    private Long taskId;
    private String subtask;

    public SubTask() {
    }

    public SubTask(Long taskId, String subtask) {
        this.taskId = taskId;
        this.subtask = subtask;
    }
}
