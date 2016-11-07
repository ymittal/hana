package finalproject.csci205.com.ymca.model.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {
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

    public void addItem(Task newTask) {
        tasks.add(newTask);
        newTask.save();
        notifyDataSetChanged();
    }
}
