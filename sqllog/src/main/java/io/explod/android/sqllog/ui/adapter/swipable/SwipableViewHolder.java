package io.explod.android.sqllog.ui.adapter.swipable;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.explod.android.sqllog.R;


public abstract class SwipableViewHolder extends RecyclerView.ViewHolder implements SwipeCallback.Swipable {

	public SwipableViewHolder(View itemView) {
		super(itemView);
	}

	@Override
	public void onSwiping() {
		itemView.setBackgroundResource(R.drawable.background_swiping);
	}

	@Override
	public void onSwipingStopped() {
		itemView.setBackgroundResource(0);
	}
}
