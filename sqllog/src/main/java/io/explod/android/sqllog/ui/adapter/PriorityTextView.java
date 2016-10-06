package io.explod.android.sqllog.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import io.explod.android.sqllog.R;


public class PriorityTextView extends TextView {

	public PriorityTextView(Context context) {
		super(context);
	}

	public PriorityTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PriorityTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public PriorityTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void setPriorityColor(int priority) {
		@ColorRes int color;
		switch (priority) {
			case Log.VERBOSE:
				color = R.color.priority_verbose;
				break;
			case Log.DEBUG:
				color = R.color.priority_debug;
				break;
			case Log.INFO:
				color = R.color.priority_info;
				break;
			case Log.WARN:
				color = R.color.priority_warn;
				break;
			case Log.ERROR:
				color = R.color.priority_error;
				break;
			case Log.ASSERT:
				color = R.color.priority_assert;
				break;
			default:
				throw new IllegalArgumentException("Invalid priority");
		}
		setTextColor(ContextCompat.getColor(getContext(), color));
	}

	public void setPriorityText(int priority) {
		@StringRes int text;
		switch (priority) {
			case Log.VERBOSE:
				text = R.string.priority_verbose;
				break;
			case Log.DEBUG:
				text = R.string.priority_debug;
				break;
			case Log.INFO:
				text = R.string.priority_info;
				break;
			case Log.WARN:
				text = R.string.priority_warn;
				break;
			case Log.ERROR:
				text = R.string.priority_error;
				break;
			case Log.ASSERT:
				text = R.string.priority_assert;
				break;
			default:
				throw new IllegalArgumentException("Invalid priority");
		}
		setText(text);
	}
}
