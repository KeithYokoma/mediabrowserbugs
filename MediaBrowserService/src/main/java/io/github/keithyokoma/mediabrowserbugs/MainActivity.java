package io.github.keithyokoma.mediabrowserbugs;

import android.content.ComponentName;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private MediaBrowserCompat mMediaBrowserCompat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMediaBrowserCompat = new MediaBrowserCompat(this, new ComponentName("io.github.keithyokoma.mediabrowserbugs", "io.github.keithyokoma.mediabrowserbugs.ConcreteMediaBrowserService"), new MediaBrowserCompat.ConnectionCallback() {
			@Override
			public void onConnected() {
				Log.d(TAG, "onConnected");
			}

			@Override
			public void onConnectionFailed() {
				Log.e(TAG, "onConnectionFailed");
			}

			@Override
			public void onConnectionSuspended() {
				Log.e(TAG, "onConnectionSuspended");
			}
		}, new Bundle());
		mMediaBrowserCompat.connect();
	}

	@Override
	protected void onDestroy() {
		if (mMediaBrowserCompat.isConnected()) {
			mMediaBrowserCompat.unsubscribe(mMediaBrowserCompat.getRoot());
		}
		mMediaBrowserCompat.disconnect();
		super.onDestroy();
	}

	public void onClickSearch(View v) {
		if (mMediaBrowserCompat == null || !mMediaBrowserCompat.isConnected())
			return;
		Log.d(TAG, "onClickSearch");
		// should show media item info in the logcat, but will crash
		mMediaBrowserCompat.search("query", new Bundle(), new MediaBrowserCompat.SearchCallback() {
			@Override
			public void onSearchResult(@NonNull String query, Bundle extras, @NonNull List<MediaBrowserCompat.MediaItem> items) {
				Log.d(TAG, "onSearchResult: q=" + query);
				for (MediaBrowserCompat.MediaItem item : items) {
					Log.d(TAG, "item=" + item);
				}
			}

			@Override
			public void onError(@NonNull String query, Bundle extras) {
				Log.e(TAG, "onError for search: q=" + query);
			}
		});
	}

	public void onClickLoadItem(View v) {
		if (mMediaBrowserCompat == null || !mMediaBrowserCompat.isConnected())
			return;
		// should show media item info in the logcat
		Log.d(TAG, "onClickLoadItem");
		mMediaBrowserCompat.getItem("__ROOT__", new MediaBrowserCompat.ItemCallback() {
			@Override
			public void onItemLoaded(MediaBrowserCompat.MediaItem item) {
				Log.d(TAG, "onItemLoaded: item=" + item);
			}

			@Override
			public void onError(@NonNull String itemId) {
				Log.e(TAG, "onError for load item: itemId=" + itemId);
			}
		});
	}

	public void onClickLoadChildren(View v) {
		if (mMediaBrowserCompat == null || !mMediaBrowserCompat.isConnected())
			return;
		// should show media item info in the logcat
		Log.d(TAG, "onClickLoadChildren");
		mMediaBrowserCompat.subscribe(mMediaBrowserCompat.getRoot(), new MediaBrowserCompat.SubscriptionCallback() {
			@Override
			public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {
				Log.d(TAG, "onChildrenLoaded: parent=" + parentId);
				for (MediaBrowserCompat.MediaItem item : children) {
					Log.d(TAG, "item=" + item);
				}
			}

			@Override
			public void onError(@NonNull String parentId) {
				Log.e(TAG, "onError for subscribe: parent=" + parentId);
			}
		});
	}
}
