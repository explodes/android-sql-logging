package io.explod.android.timbersql.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.Date;

public class LogEntryCursor {

	public static long getId(@NonNull Cursor cursor) {
		return cursor.getLong(cursor.getColumnIndex(LogEntryContract.LogEntryColumns._ID));
	}

	public static long getTimestamp(@NonNull Cursor cursor) {
		return cursor.getLong(cursor.getColumnIndex(LogEntryContract.LogEntryColumns.TIMESTAMP));
	}

	public static Date getDate(@NonNull Cursor cursor) {
		return new Date(getTimestamp(cursor));
	}

	public static int getPriority(@NonNull Cursor cursor) {
		return cursor.getInt(cursor.getColumnIndex(LogEntryContract.LogEntryColumns.PRIORITY));
	}

	@NonNull
	public static String getTag(@NonNull Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex(LogEntryContract.LogEntryColumns.TAG));
	}

	@NonNull
	public static String getMessage(@NonNull Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex(LogEntryContract.LogEntryColumns.MESSAGE));
	}

	@NonNull
	public static LogEntry getLogEntry(@NonNull Cursor cursor) {
		return new LogEntry(
			LogEntryCursor.getTimestamp(cursor),
			LogEntryCursor.getPriority(cursor),
			LogEntryCursor.getTag(cursor),
			LogEntryCursor.getMessage(cursor)
		);
	}
}
