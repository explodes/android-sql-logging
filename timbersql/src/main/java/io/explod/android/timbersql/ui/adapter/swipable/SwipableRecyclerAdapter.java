package io.explod.android.timbersql.ui.adapter.swipable;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public abstract class SwipableRecyclerAdapter<VH extends SwipableViewHolder & SwipeCallback.Swipable> extends RecyclerView.Adapter<VH> implements SwipeCallback.OnSwiped {

	@Nullable
	private SwipeCallback mSwipeCallback;

	@Nullable
	private ItemTouchHelper mItemTouchHelper;

	public void attachSwipeToRecyclerView(@NonNull RecyclerView recyclerView) {
		if (mSwipeCallback == null) {
			mSwipeCallback = new SwipeCallback(this);
		}
		if (mItemTouchHelper == null) {
			mItemTouchHelper = new ItemTouchHelper(mSwipeCallback);
		}
		mItemTouchHelper.attachToRecyclerView(recyclerView);
	}

}
