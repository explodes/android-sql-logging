package io.explod.android.sqllog.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import java.lang.ref.WeakReference;

public class PreferenceSelectionDialog {

	public interface OnSelection {
		void onSelection(int index);
	}

	public static void show(@NonNull Context context, @StringRes int title, @ArrayRes int names, @NonNull final OnSelection callback) {
		final WeakReference<OnSelection> callbackRef = new WeakReference<>(callback);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setItems(names, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				OnSelection callbackSafe = callbackRef.get();
				if (callbackSafe != null) {
					callback.onSelection(which);
				}
			}
		});
		builder.setNegativeButton(android.R.string.cancel, null);
		builder.create().show();
	}
}
