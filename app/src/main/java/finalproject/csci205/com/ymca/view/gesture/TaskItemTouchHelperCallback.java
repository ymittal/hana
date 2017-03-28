/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package finalproject.csci205.com.ymca.view.gesture;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * A class to enable swipe and drag gestures on <code>{@link RecyclerView}</code>. This file
 * has been modified by {@author Yash Mittal} to meet application specific needs.
 *
 * @see <a href="https://github.com/iPaulPro/Android-ItemTouchHelper-Demo">
 * GitHub Android-ItemTouchHelper-Demo</a>
 */
public class TaskItemTouchHelperCallback extends ItemTouchHelper.Callback {

    /**
     * Adapter attached to recyclerview
     */
    private final TaskTouchHelperAdapter mAdapter;
    /**
     * RecyclerView used
     */
    private final RecyclerView recyclerView;

    /**
     * @param adapter      adapter attached to recyclerview
     * @param recyclerView recyclerview object
     */
    public TaskItemTouchHelperCallback(TaskTouchHelperAdapter adapter, RecyclerView recyclerView) {
        mAdapter = adapter;
        this.recyclerView = recyclerView;
    }

    /**
     * Disables long press on recyclerview items
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * Enables swipe gesture on recyclerview items
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * Returns the different movement flags for drag and swipe gestures
     *
     * @param recyclerView recyclerview
     * @param viewHolder   recyclerview viewholder {@link finalproject.csci205.com.ymca.view.task.item.TasksAdapter.TaskViewHolder}
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * @param recyclerView recyclerview
     * @param viewHolder   recyclerview viewholder {@link finalproject.csci205.com.ymca.view.task.item.TasksAdapter.TaskViewHolder}
     * @param target       target position of item in recyclerview
     * @return false
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }


    /**
     * Handles swipe gesture by class {@link finalproject.csci205.com.ymca.view.task.item.TasksAdapter#onItemDismiss(int, RecyclerView)}
     *
     * @param viewHolder recyclerview viewholder {@link finalproject.csci205.com.ymca.view.task.item.TasksAdapter.TaskViewHolder}
     * @param direction  direction of swipe
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition(), recyclerView);
    }
}