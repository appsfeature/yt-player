package com.ytplayer;

import android.app.Activity;
import android.content.Context;
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
    private final Context context;
    private VideoType playerType;

    private YTPlayer(Context context, String googleApiKey) {
        this.context = context;
        this.playerType = VideoType.OPEN_INTERNAL_PLAYER;
        YTConfig.setApiKey(googleApiKey);
    }

    public static YTPlayer getInstance(Context context, String googleApiKey) {
        if (instance == null) {
            instance = new YTPlayer(context, googleApiKey);
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

    /**
     *  Used for In app open
     */
    public void openVideo(String videoId, boolean isDetailVisible) {
        YTConfig.setVideoId(videoId);
        if (playerType == VideoType.OPEN_INTERNAL_PLAYER) {
            YTUtility.openInternalYoutubePlayer(context);
        }
        if (playerType == VideoType.OPEN_INTERNAL_SLIDING_PLAYER) {
            YTUtility.openInternalYoutubePlayer(context, isDetailVisible);
        }
    }
    /**
     *  Used for External app open
     */
    public void openVideo(Activity activity, String videoId, boolean isDetailVisible) {
        YTUtility.openExternalYoutubeVideoPlayer(activity, YTConfig.getApiKey(), YTConfig.getVideoId());
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

    public void openPlaylistExternal(Activity activity, String playlistId) {
        YTConfig.setPlaylistId(playlistId);
        YTUtility.openExternalYoutubePlaylistPlayer(activity, YTConfig.getApiKey(), YTConfig.getPlaylistId());
    }

    public void openViewPlaylist(ArrayList<YTVideoModel> playlist) {
        openViewPlaylist(null, playlist);
    }

    public void openViewPlaylist(String playerName, ArrayList<YTVideoModel> playlist) {
        YTUtility.openInternalYoutubePlaylistPlayer(context, playerName, playlist);
    }

    public void openPlaylist(String playerName, String playListId) {
        YTUtility.openInternalYoutubeByPlaylistId(context, playerName, playListId);
    }

    /**
     * @param playerName  Toolbar title name
     * @param playListIds Playlist ids in comma separated
     */
    public void openPlaylistMultipleIds(String playerName, String playListIds) {
        YTUtility.openInternalYoutubeByPlayListMultipleIds(context, playerName, playListIds);
    }

    public void openChannel(String playerName, String channelId) {
        YTUtility.openInternalYoutubeByChannelId(context, playerName, channelId);
    }

    public void openSearch(String youtubeChannelId) {
        YTUtility.openInternalYoutubePlaylistPlayer(context, youtubeChannelId);
    }

    public void openSubscribeOption(String youtubeChannelId) {
        YTUtility.openInternalYoutubeSubscribe(context, youtubeChannelId);
    }

}
