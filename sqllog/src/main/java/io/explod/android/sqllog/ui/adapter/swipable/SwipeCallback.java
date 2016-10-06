package io.explod.android.sqllog.ui.adapter.swipable;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SwipeCallback extends ItemTouchHelper.SimpleCallback {

	public interface OnSwiped {
		void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder);
	}

	public interface Swipable {
		void onSwiping();

		void onSwipingStopped();
	}

	@NonNull
	private final OnSwiped mOnSwiped;

	public SwipeCallback(@NonNull OnSwiped onSwiped) {
		super(0, ItemTouchHelper.START | ItemTouchHelper.END);
		mOnSwiped = onSwiped;
	}

	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
		return false;
	}

	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
		mOnSwiped.onSwiped(viewHolder);
	}

	@Override
	public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
		if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
			// Fade out the view as it is swiped out of the parent's bounds
			final float alpha = 1f - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
			viewHolder.itemView.setAlpha(alpha);
			viewHolder.itemView.setTranslationX(dX);
		} else {
			super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
		}
	}

	@Override
	public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
		if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder instanceof Swipable) {
			((Swipable) viewHolder).onSwiping();
		}
		super.onSelectedChanged(viewHolder, actionState);
	}

	@Override
	public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
		super.clearView(recyclerView, viewHolder);

		viewHolder.itemView.setAlpha(1.0f);

		if (viewHolder instanceof Swipable) {
			((Swipable) viewHolder).onSwipingStopped();
		}
	}

}
