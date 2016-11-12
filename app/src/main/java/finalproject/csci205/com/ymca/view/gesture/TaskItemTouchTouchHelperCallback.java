package finalproject.csci205.com.ymca.view.gesture;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class TaskItemTouchTouchHelperCallback extends ItemTouchHelper.Callback {

    private final TaskTouchHelperAdapter mAdapter;
    private RecyclerView recyclerView;

    public TaskItemTouchTouchHelperCallback(TaskTouchHelperAdapter adapter, RecyclerView recyclerView) {
        mAdapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition(), recyclerView);
    }
}