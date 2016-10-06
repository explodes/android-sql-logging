package io.explod.android.timbersql;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import io.explod.android.timbersql.data.LogEntry;
import io.explod.android.timbersql.data.LogEntryProvider;
import timber.log.Timber;

public class SqlLoggingTree extends Timber.Tree {

	@NonNull
	private final Context mContext;

	@NonNull
	private final String mDefaultTag;

	public SqlLoggingTree(@NonNull Context appContext, @NonNull String defaultTag) {
		mContext = appContext;
		mDefaultTag = defaultTag;
	}

	@Override
	protected void log(int priority, @Nullable String tag, @NonNull String message, @Nullable Throwable t) {
		if (TextUtils.isEmpty(tag)) tag = mDefaultTag;
		LogEntry entry = LogEntry.createLogEntry(priority, tag, message);
		LogEntryProvider.insertLogEntry(mContext, entry);
	}

}
