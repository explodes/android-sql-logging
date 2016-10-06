package io.explod.android.timbersql.sample;

import android.app.Application;

import io.explod.android.timbersql.SqlLoggingTree;
import timber.log.Timber;


public class App extends Application {

	private static final String DEFAULT_TAG = "TimberSqlSampleApp";

	@Override
	public void onCreate() {
		Timber.plant(new Timber.DebugTree(), new SqlLoggingTree(this, DEFAULT_TAG));
	}
}
