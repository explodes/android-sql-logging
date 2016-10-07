Timber SQL Logger
=================
[![License](http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](http://www.apache.org/licenses/LICENSE-2.0)
[![API](https://img.shields.io/badge/API-11%2B-blue.svg?style=flat-square)](https://developer.android.com/about/versions/android-3.0.html)

 ![Screenshot of in-app log viewer][screenshot]

Log to a sql database for debugging. View the logs in-app with the LogViewerActivity.

This library was created when I decided that having my phone attached to logcat for hours in hopes a
heisenbug would appear in a service was too inefficient.

Works with API level 11+

Usage
-----

Logging is done manually by wait of...
```java
    LogEntry entry = LogEntry.createLogEntry(Log.DEBUG, TAG, "Hit critical function");
    LogEntryProvider.insertLogEntry(context, entry);
```

...or you can plant a tree with Timber using `SQLLoggingTree`:

```java
public class App extends Application {

    // The default tag is used when no tag is provided.
	private static final String DEFAULT_TAG = "TimberSqlSampleApp";

	@Override
	public void onCreate() {
	    LogEntryProvider.initialize(this);
		Timber.plant(new SqlLoggingTree(this, DEFAULT_TAG));
	}
}
```

Setup
-----

In your app's AndroidManifest, you will need to include two items:
```xml
<activity
    android:name="io.explod.android.sqllog.ui.activity.LogViewerActivity"
    android:exported="false"/>

<!-- Important: set your own authority name and provide that name in the metadata -->

<provider
    android:name="io.explod.android.sqllog.data.LogEntryProvider"
    android:authorities="your.authority.name"
    android:exported="false"/>

<meta-data android:name="io.explod.sqllog.log.authority" android:value="your.authority.name"/>
```

And finally, to view the logs, you start the activity:
```java
	private void goToLogViewer() {
		Intent intent = new Intent(this, LogViewerActivity.class);
		startActivity(intent);
	}
```

License
-------

    Copyright 2016 Evan Leis

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 [screenshot]: http://i.imgur.com/tneAUAF.png
