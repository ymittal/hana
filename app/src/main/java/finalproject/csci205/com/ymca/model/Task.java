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
        this.title = "";
        this.desc = "";
        this.isComplete = false;
    }

    public Task(String title) {
        this.title = title;
        this.desc = "";
        this.isComplete = false;
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
