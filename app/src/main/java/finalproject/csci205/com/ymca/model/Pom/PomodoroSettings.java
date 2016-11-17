package finalproject.csci205.com.ymca.model.Pom;

import com.orm.SugarRecord;

/**
 * Created by ceh024 on 11/6/16.
 */

public class PomodoroSettings extends SugarRecord {
    private PomBundle bundle;

    public PomodoroSettings(PomBundle bundle) {
        this.bundle = bundle;
    }


}
