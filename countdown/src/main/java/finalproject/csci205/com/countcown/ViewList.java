package finalproject.csci205.com.countcown;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by ceh024 on 11/16/16.
 */

public class ViewList extends ArrayList {
    private View view;
    private String tag;
    private ArrayList<String> tagRef;

    public ViewList() {
        tagRef = new ArrayList(10);
    }


    public void add(Object view, String tag) {
        this.add(view);
        tagRef.add(tag);
    }

    public Object get(String tag) {
        for (int i = 0; i < tagRef.size(); i++) {
            if (tag.equalsIgnoreCase(tagRef.get(i))) {
                return this.get(i);
            }

        }
        return null;
    }

    public void unbindView(String tag) {
        for (int i = 0; i < tagRef.size(); i++) {
            if (tag.equalsIgnoreCase(tagRef.get(i))) {
                tagRef.remove(i);
                this.remove(i);
            }

        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
