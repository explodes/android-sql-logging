package io.explod.android.sqllog.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class LogEntryContract {

	public static final String AUTHORITY = "io.explod.android.timbersql.logentry";

	public static final String TABLE = "log";

	public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
		.authority(AUTHORITY)
		.appendPath(TABLE)
		.build();

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "." + TABLE;
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "." + TABLE;

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
