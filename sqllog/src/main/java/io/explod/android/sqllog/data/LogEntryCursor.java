package io.explod.android.sqllog.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.Date;

public class LogEntryCursor {

	public static long getId(@NonNull Cursor cursor) {
		return cursor.getLong(cursor.getColumnIndex(LogEntryContract.Columns._ID));
	}

	public static long getTimestamp(@NonNull Cursor cursor) {
		return cursor.getLong(cursor.getColumnIndex(LogEntryContract.Columns.TIMESTAMP));
	}

	public static Date getDate(@NonNull Cursor cursor) {
		return new Date(getTimestamp(cursor));
	}

	public static int getPriority(@NonNull Cursor cursor) {
		return cursor.getInt(cursor.getColumnIndex(LogEntryContract.Columns.PRIORITY));
	}

	@NonNull
	public static String getTag(@NonNull Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex(LogEntryContract.Columns.TAG));
	}

	@NonNull
	public static String getMessage(@NonNull Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex(LogEntryContract.Columns.MESSAGE));
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
