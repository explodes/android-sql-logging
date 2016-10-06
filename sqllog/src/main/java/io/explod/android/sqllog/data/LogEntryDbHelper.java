package io.explod.android.sqllog.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogEntryDbHelper extends SQLiteOpenHelper {

	private static final int VERSION = 2;

	private static final String CREATE_TABLE = "CREATE TABLE " +
		LogEntryContract.TABLE +
		"(" +
		LogEntryContract.Columns._ID + " INTEGER PRIMARY KEY, " +
		LogEntryContract.Columns.TIMESTAMP + " INTEGER, " +
		LogEntryContract.Columns.PRIORITY + " INTEGER, " +
		LogEntryContract.Columns.TAG + " TEXT NOT NULL, " +
		LogEntryContract.Columns.MESSAGE + " TEXT NOT NULL " +
		")";

	public LogEntryDbHelper(Context context) {
		super(context, LogEntryContract.TABLE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// nuke and rebuild to reload data
		db.execSQL("drop table if exists " + LogEntryContract.TABLE);
		onCreate(db);
	}

}
