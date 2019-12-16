package com.ytplayer;

import android.app.Activity;
import android.graphics.Typeface;

import com.ytplayer.adapter.YTVideoModel;
import com.ytplayer.util.YTConfig;

import java.util.ArrayList;

/**
 * @author Created by Abhijit on 2/6/2018.
 */
public class YTPlayer {

    private Typeface typeface;


    public enum VideoType {
        OPEN_INTERNAL_PLAYER,
        OPEN_INTERNAL_SLIDING_PLAYER,
        OPEN_EXTERNAL
    }

    private static YTPlayer instance;
    private final Activity activity;
    private final String googleApiKey;
    private VideoType playerType;

    public YTPlayer(Activity activity, String googleApiKey) {
        this.activity = activity;
        this.googleApiKey = googleApiKey;
        this.playerType = VideoType.OPEN_INTERNAL_PLAYER;
        YTConfig.setApiKey(googleApiKey);
    }

    public static YTPlayer getInstance(Activity activity, String googleApiKey) {
        if (instance == null) {
            instance = new YTPlayer(activity, googleApiKey);
        }
        return instance;
    }

    public static YTPlayer getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("Use getInstance() method to get the single instance of this class.");
        }
        return instance;
    }

    public void openVideo(String videoId) {
        openVideo(videoId, false);
    }

    public void openVideo(String videoId, boolean isDetailVisible) {
        YTConfig.setVideoId(videoId);
        if (playerType == VideoType.OPEN_INTERNAL_PLAYER) {
            YTUtility.openInternalYoutubePlayer(activity);
        }
        if (playerType == VideoType.OPEN_INTERNAL_SLIDING_PLAYER) {
            YTUtility.openInternalYoutubePlayer(activity, isDetailVisible);
        } else {
            YTUtility.openExternalYoutubeVideoPlayer(activity, YTConfig.getApiKey(), YTConfig.getVideoId());
        }
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public YTPlayer setTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public YTPlayer maxListItemsCount(int maxResultsCount) {
        YTConfig.setMaxResultsCount(maxResultsCount);
        return this;
    }

    public YTPlayer setPlayerType(VideoType playerType) {
        this.playerType = playerType;
        return this;
    }

    public void openPlaylistExternal(String playlistId) {
        YTConfig.setPlaylistId(playlistId);
        YTUtility.openExternalYoutubePlaylistPlayer(activity, YTConfig.getApiKey(), YTConfig.getPlaylistId());
    }

    public void openViewPlaylist(ArrayList<YTVideoModel> playlist) {
        openViewPlaylist(null, playlist);
    }

    public void openViewPlaylist(String playerName, ArrayList<YTVideoModel> playlist) {
        YTUtility.openInternalYoutubePlaylistPlayer(activity, playerName, playlist);
    }

    public void openPlaylist(String playerName, String playListId) {
        YTUtility.openInternalYoutubeByPlaylistId(activity, playerName, playListId);
    }

    /**
     * @param playerName  Toolbar title name
     * @param playListIds Playlist ids in comma separated
     */
    public void openPlaylistMultipleIds(String playerName, String playListIds) {
        YTUtility.openInternalYoutubeByPlayListMultipleIds(activity, playerName, playListIds);
    }

    public void openChannel(String playerName, String channelId) {
        YTUtility.openInternalYoutubeByChannelId(activity, playerName, channelId);
    }

    public void openSearch(String youtubeChannelId) {
        YTUtility.openInternalYoutubePlaylistPlayer(activity, youtubeChannelId);
    }

    public void openSubscribeOption(String youtubeChannelId) {
        YTUtility.openInternalYoutubeSubscribe(activity, youtubeChannelId);
    }

}
