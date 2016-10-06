package io.explod.android.sqllog.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

public class LogEntryContract {

	public static final String TABLE = "log";

	public static String AUTHORITY;
	public static Uri CONTENT_URI;
	public static String CONTENT_TYPE_DIR;
	public static String CONTENT_TYPE_ITEM;

	static void setAuthority(@NonNull String authority) {
		CONTENT_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "." + TABLE;
		CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "." + TABLE;
		AUTHORITY = authority;
		CONTENT_URI = new Uri.Builder().scheme("content")
			.authority(AUTHORITY)
			.appendPath(TABLE)
			.build();
	}


	public static final class Columns implements BaseColumns {
		public static final String TIMESTAMP = "timestamp"; // long
		public static final String PRIORITY = "priority"; // int
		public static final String TAG = "tag"; // string
		public static final String MESSAGE = "message"; // string
	}

	public static final class Projection {
		public static final String[] ALL = {
			Columns._ID, Columns.TIMESTAMP, Columns.PRIORITY, Columns.TAG, Columns.MESSAGE};
	}

	public static final class Sort {
		public static final String DATE = Columns.TIMESTAMP + " DESC";
		public static final String PRIORITY = Columns.PRIORITY + " DESC, " + Columns.TIMESTAMP + " DESC";
		public static final String DEFAULT = DATE;
	}

}
