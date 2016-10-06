package io.explod.android.sqllog.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class LogEntry implements Parcelable {

	public static LogEntry create(int priority, @NonNull String tag, @Nullable String message) {
		return create(priority, tag, message, null);
	}

	public static LogEntry create(int priority, @NonNull String tag, @Nullable String message, @Nullable Throwable t) {
		return new LogEntry(
			System.currentTimeMillis(),
			priority,
			tag,
			buildMessage(message, t)
		);
	}

	@NonNull
	private static String buildMessage(@Nullable String message, @Nullable Throwable t) {
		if (t != null) {
			StringWriter sw = new StringWriter(256);
			if (!TextUtils.isEmpty(message)) {
				sw.append(message);
				sw.append('\n');
			}
			PrintWriter pw = new PrintWriter(sw, false);
			t.printStackTrace(pw);
			pw.flush();
			return sw.toString();
		}
		return TextUtils.isEmpty(message) ? "" : message;
	}

	public final long timestamp;
	public final int priority;
	public final String tag;
	public final String message;

	public LogEntry(long timestamp, int priority, @NonNull String tag, @NonNull String message) {
		this.timestamp = timestamp;
		this.priority = priority;
		this.tag = tag;
		this.message = message;
	}

	protected LogEntry(@NonNull Parcel in) {
		this.timestamp = in.readLong();
		this.priority = in.readInt();
		this.tag = in.readString();
		this.message = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(timestamp);
		dest.writeInt(priority);
		dest.writeString(tag);
		dest.writeString(message);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public String toString() {
		return "LogEntry:" + tag + "/" + message;
	}

	public static final Creator<LogEntry> CREATOR = new Creator<LogEntry>() {
		@Override
		public LogEntry createFromParcel(Parcel in) {
			return new LogEntry(in);
		}

		@Override
		public LogEntry[] newArray(int size) {
			return new LogEntry[size];
		}
	};

}
