package io.github.keithyokoma.mediabrowserbugs;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KeishinYokomaku on 2017/03/30.
 */
// Simply just send a fake response to client MediaBrowsers
public class ConcreteMediaBrowserService extends MediaBrowserServiceCompat {
	private MediaSessionCompat mMediaSessionCompat;

	@Override
	public void onCreate() {
		super.onCreate();
		mMediaSessionCompat = new MediaSessionCompat(this, "sample_session");
		setSessionToken(mMediaSessionCompat.getSessionToken());
	}

	@Override
	public void onDestroy() {
		mMediaSessionCompat.release();
		super.onDestroy();
	}

	@Nullable
	@Override
	public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
		return new BrowserRoot("__ROOT__", new Bundle()); // no package verification for simplifying the problem
	}

	@Override
	public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
		List<MediaBrowserCompat.MediaItem> list = new ArrayList<>();
		list.add(new MediaBrowserCompat.MediaItem(new MediaDescriptionCompat.Builder()
				.setMediaId("__PLAYLIST__")
				.setTitle("Playlist")
				.setSubtitle("Custom Playlist")
				.setDescription("Custom Playlist")
				.setMediaUri(Uri.EMPTY)
				.build(), MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
		result.sendResult(list);
	}

	@Override
	public void onLoadItem(String itemId, @NonNull Result<MediaBrowserCompat.MediaItem> result) {
		result.sendResult(new MediaBrowserCompat.MediaItem(new MediaDescriptionCompat.Builder()
				.setMediaId("__PLAYLIST__")
				.setTitle("Playlist")
				.setSubtitle("Custom Playlist")
				.setDescription("Custom Playlist")
				.setMediaUri(Uri.EMPTY)
				.build(), MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
	}

	@Override
	public void onSearch(@NonNull String query, Bundle extras, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
		List<MediaBrowserCompat.MediaItem> list = new ArrayList<>();
		list.add(new MediaBrowserCompat.MediaItem(new MediaDescriptionCompat.Builder()
				.setMediaId("__PLAYLIST__")
				.setTitle("Playlist")
				.setSubtitle("Custom Playlist")
				.setDescription("Custom Playlist")
				.setMediaUri(Uri.EMPTY)
				.build(), MediaBrowserCompat.MediaItem.FLAG_BROWSABLE));
		result.sendResult(list);
	}
}
