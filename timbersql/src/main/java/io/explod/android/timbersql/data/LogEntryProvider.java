package io.explod.android.timbersql.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


public class LogEntryProvider extends ContentProvider {

	private static final String TAG = LogEntryProvider.class.getSimpleName();

	private static final int LOG_ENTRY_TABLE = 1;
	private static final int LOG_ENTRY_ROW = 2;

	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		sUriMatcher.addURI(LogEntryContract.AUTHORITY, LogEntryContract.LogEntry.TABLE, LOG_ENTRY_TABLE);
		sUriMatcher.addURI(LogEntryContract.AUTHORITY, LogEntryContract.LogEntry.TABLE + "/#", LOG_ENTRY_ROW);
	}

	private LogEntryDbHelper mDbHelper;

	@Override
	public boolean onCreate() {
		try {
			mDbHelper = new LogEntryDbHelper(getContext());
			return true;
		} catch (Exception ex) {
			Log.e(TAG, "LogEntryProvider not available", ex);
			return false;
		}
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		switch (sUriMatcher.match(uri)) {
			case LOG_ENTRY_TABLE:
				return LogEntryContract.LogEntry.CONTENT_TYPE;
			case LOG_ENTRY_ROW:
				return LogEntryContract.LogEntry.CONTENT_ITEM_TYPE;
			default:
				return null;
		}
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
						@Nullable String selection, @Nullable String[] selectionArgs,
						@Nullable String sortOrder) {


		switch (sUriMatcher.match(uri)) {
			case LOG_ENTRY_TABLE: {
				SQLiteDatabase db = mDbHelper.getReadableDatabase();
				if (TextUtils.isEmpty(sortOrder))
					sortOrder = LogEntryContract.LogEntry.SORT_DEFAULT;

				return db.query(LogEntryContract.LogEntry.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			case LOG_ENTRY_ROW: {
				SQLiteDatabase db = mDbHelper.getReadableDatabase();
				if (TextUtils.isEmpty(sortOrder))
					sortOrder = LogEntryContract.LogEntry.SORT_DEFAULT;

				long id = ContentUris.parseId(uri);
				String where = LogEntryContract.LogEntryColumns._ID + " = " + id;
				if (!TextUtils.isEmpty(selection)) {
					where += " AND " + selection;
				}

				return db.query(LogEntryContract.LogEntry.TABLE, projection, where, selectionArgs, null, null, sortOrder);
			}
			default:
				return null;
		}

	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

		switch (sUriMatcher.match(uri)) {
			case LOG_ENTRY_TABLE: {
				SQLiteDatabase db = mDbHelper.getWritableDatabase();
				long id = db.insert(LogEntryContract.LogEntry.TABLE, null, values);
				if (id == -1) {
					throw new SQLException("failed to insert record into " + uri);
				}
				notifyChange(LogEntryContract.LogEntry.CONTENT_URI);
				return ContentUris.withAppendedId(LogEntryContract.LogEntry.CONTENT_URI, id);
			}
			default:
				throw new IllegalArgumentException("Unsupported insert uri: " + uri);
		}
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		switch (sUriMatcher.match(uri)) {
			case LOG_ENTRY_TABLE: {
				SQLiteDatabase db = mDbHelper.getWritableDatabase();

				// hack-fix bug where empty selection doesn't return count
				if (TextUtils.isEmpty(selection)) selection = "1";

				int count = db.delete(LogEntryContract.LogEntry.TABLE, selection, selectionArgs);

				if (count > 0) {
					notifyChange(LogEntryContract.LogEntry.CONTENT_URI);
				}

				return count;
			}
			case LOG_ENTRY_ROW: {
				SQLiteDatabase db = mDbHelper.getWritableDatabase();

				long id = ContentUris.parseId(uri);
				String where = LogEntryContract.LogEntryColumns._ID + " = " + id;
				if (!TextUtils.isEmpty(selection)) {
					where += " AND " + selection;
				}

				int count = db.delete(LogEntryContract.LogEntry.TABLE, where, selectionArgs);

				if (count > 0) {
					notifyChange(LogEntryContract.LogEntry.CONTENT_URI);
				}

				return count;
			}
			default:
				return 0;
		}
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values,
					  @Nullable String selection, @Nullable String[] selectionArgs) {
		switch (sUriMatcher.match(uri)) {
			case LOG_ENTRY_TABLE: {
				SQLiteDatabase db = mDbHelper.getWritableDatabase();

				// hack-fix bug where empty selection doesn't return count
				if (TextUtils.isEmpty(selection)) selection = "1";

				int count = db.update(LogEntryContract.LogEntry.TABLE, values, selection, selectionArgs);

				if (count > 0) {
					notifyChange(LogEntryContract.LogEntry.CONTENT_URI);
				}

				return count;
			}
			case LOG_ENTRY_ROW: {
				SQLiteDatabase db = mDbHelper.getWritableDatabase();

				long id = ContentUris.parseId(uri);
				String where = LogEntryContract.LogEntryColumns._ID + " = " + id;
				if (!TextUtils.isEmpty(selection)) {
					where += " AND " + selection;
				}

				int count = db.update(LogEntryContract.LogEntry.TABLE, values, where, selectionArgs);

				if (count > 0) {
					notifyChange(LogEntryContract.LogEntry.CONTENT_URI);
				}

				return count;
			}
			default:
				return 0;
		}
	}

	private void notifyChange(@NonNull Uri uri) {
		Context context = getContext();
		if (context == null) return;

		ContentResolver resolver = context.getContentResolver();
		if (resolver == null) return;

		resolver.notifyChange(uri, null);
	}

	@NonNull
	private static ContentValues contentValuesOf(@NonNull LogEntry logEntry) {
		ContentValues values = new ContentValues(5);
		values.put(LogEntryContract.LogEntryColumns.TIMESTAMP, logEntry.timestamp);
		values.put(LogEntryContract.LogEntryColumns.PRIORITY, logEntry.priority);
		values.put(LogEntryContract.LogEntryColumns.TAG, logEntry.tag);
		values.put(LogEntryContract.LogEntryColumns.MESSAGE, logEntry.message);
		return values;
	}

	@Nullable
	public static Uri insertLogEntry(@NonNull Context context, @NonNull LogEntry logEntry) {
		ContentResolver resolver = context.getContentResolver();
		ContentValues contentValues = contentValuesOf(logEntry);
		return resolver.insert(LogEntryContract.LogEntry.CONTENT_URI, contentValues);
	}

	public static boolean deleteLogEntry(@NonNull Context context, long logEntryId) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = ContentUris.withAppendedId(LogEntryContract.LogEntry.CONTENT_URI, logEntryId);
		return 1 == resolver.delete(uri, null, null);
	}

	public static int deleteAllLogEntries(@NonNull Context context) {
		return deleteLogEntries(context, null, null);
	}

	public static int deleteLogEntries(@NonNull Context context, @Nullable String selection, @Nullable String[] selectionArgs) {
		ContentResolver resolver = context.getContentResolver();
		return resolver.delete(LogEntryContract.LogEntry.CONTENT_URI, selection, selectionArgs);
	}

}