package finalproject.csci205.com.ymca.model.item;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.view.gesture.TaskTouchHelperAdapter;

/*
TODO: ASK Yash on his opinion on how we should re-distribute non-obvious features.
 */

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> implements TaskTouchHelperAdapter {
    List<Task> tasks = new ArrayList<>();

    public TasksAdapter() {
        tasks = Task.listAll(Task.class);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_rv_item, parent, false);
        return new TaskViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTask.setText(task.getTitle());
        holder.checkboxTask.setChecked(task.isComplete());
        holder.checkboxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isComplete) {
                Task task = tasks.get(holder.getAdapterPosition());
                task.setIsComplete(isComplete);
                task.save();
            }
        });

        holder.itemView.setTag(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //This needs to go to presenter
    public void addItem(Task newTask) {
        tasks.add(newTask);
        newTask.save();
        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        final Task todoToBeRemoved = tasks.get(position);
        todoToBeRemoved.delete();

        tasks.remove(position); //Presenter
        notifyItemRemoved(position); //This needs to go from Presenter to View.

        Snackbar snackbar = Snackbar
                .make(recyclerView, "Item has been deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        todoToBeRemoved.save();
                        tasks.add(position, todoToBeRemoved);
                        notifyItemInserted(position);
                    }
                });
        snackbar.show(); //Showing a snackbar should exist in view, but can be triggered by the presenter
    }
}
