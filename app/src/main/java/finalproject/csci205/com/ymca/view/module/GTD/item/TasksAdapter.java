package finalproject.csci205.com.ymca.view.module.GTD.item;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.module.GTDPresenter;
import finalproject.csci205.com.ymca.view.gesture.TaskTouchHelperAdapter;

/*
TODO: ASK Yash on his opinion on how we should re-distribute non-obvious features.
    I think this should be a sublcass of the GTDFragment or considered an element of the view.
    My reasoning is that this handles a lot of the view interactions specific to the
    RecyclerView, and only handles small tidbits of logic to the model.
 */

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> implements TaskTouchHelperAdapter {
    private GTDPresenter gtdPresenter;

    public TasksAdapter(GTDPresenter gtd) {
        this.gtdPresenter = gtd;
        //tasks = Task.listAll(Task.class); //Find out what this does.
    }


    /*
        Figure out what todo with this method which skips the presenter and addressed the view
        from the model, breaking MVP.
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_rv_item, parent, false);
        return new TaskViewHolder(rowView);
    }

    /*
        This method directly accesses the view. Model --> View
        This method isn't clean, the anon inner class forces dev to create two objs of Task
        in order to do something. Makes sense to implement setOnCheckedChangeListener - Interface
        to avoid garbage code.
     */
    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        Task task = gtdPresenter.getTasks().get(position);
        holder.tvTask.setText(task.getTitle());
        holder.checkboxTask.setChecked(task.isComplete());
        holder.checkboxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isComplete) {
                /*
                    This needs to be refactored such that it goes through the presenter.
                 */
                Task task = gtdPresenter.getTasks().get(holder.getAdapterPosition());
                task.setIsComplete(isComplete);
                task.save();
            }
        });

        holder.itemView.setTag(task);
    }

    @Override
    public int getItemCount() {
        return gtdPresenter.taskSize(); //This should be returned by the presenter.
    }


    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        gtdPresenter.removeTask(position);

        Snackbar snackbar = Snackbar
                .make(recyclerView, "Item has been deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gtdPresenter.restoreTask(position);
                    }
                });
        snackbar.show(); //Showing a snackbar should exist in view, but can be triggered by the presenter
    }
}
