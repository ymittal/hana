package finalproject.csci205.com.ymca.model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by ceh024 on 11/6/16.
 */

public class Task extends SugarRecord implements Serializable {

    private String title;
    private String desc;
    private Boolean isComplete;

    public Task() {
    }

    public Task(String title, Boolean isComplete) {
        this.title = title;
        this.desc = "Empty desc";
        this.isComplete = isComplete;
    }

    public Task(String title, String desc, Boolean isComplete) {
        this.title = title;
        this.desc = desc;
        this.isComplete = isComplete;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public Boolean isComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
}
