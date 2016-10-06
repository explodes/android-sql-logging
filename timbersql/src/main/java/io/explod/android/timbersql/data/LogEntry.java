package io.explod.android.timbersql.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public final class LogEntry implements Parcelable {

	public static LogEntry createLogEntry(int priority, @NonNull String tag, @NonNull String message) {
		return new LogEntry(
			System.currentTimeMillis(),
			priority,
			tag,
			message
		);
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
