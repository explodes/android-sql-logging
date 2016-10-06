package io.explod.android.sqllog.sample;

import android.app.Application;

import io.explod.android.sqllog.data.LogEntryProvider;
import io.explod.android.sqllog.timber.SqlLoggingTree;
import timber.log.Timber;


public class App extends Application {

	private static final String DEFAULT_TAG = "TimberSqlSampleApp";

	@Override
	public void onCreate() {
		LogEntryProvider.initialize(this);
		Timber.plant(new Timber.DebugTree(), new SqlLoggingTree(this, DEFAULT_TAG));
	}
}
