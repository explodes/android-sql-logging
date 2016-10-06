package io.explod.android.timbersql.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogEntryDbHelper extends SQLiteOpenHelper {

	private static final int VERSION = 2;

	private static final String CREATE_TABLE = "CREATE TABLE " +
		LogEntryContract.LogEntry.TABLE +
		"(" +
		LogEntryContract.LogEntryColumns._ID + " INTEGER PRIMARY KEY, " +
		LogEntryContract.LogEntryColumns.TIMESTAMP + " INTEGER, " +
		LogEntryContract.LogEntryColumns.PRIORITY + " INTEGER, " +
		LogEntryContract.LogEntryColumns.TAG + " TEXT NOT NULL, " +
		LogEntryContract.LogEntryColumns.MESSAGE + " TEXT NOT NULL " +
		")";

	public LogEntryDbHelper(Context context) {
		super(context, LogEntryContract.LogEntry.TABLE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// nuke and rebuild to reload data
		db.execSQL("drop table if exists " + LogEntryContract.LogEntry.TABLE);
		onCreate(db);
	}

}
