package com.ytplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.ytplayer.activity.playlist.YTChannelPlaylistActivity;
import com.ytplayer.activity.playlist.YTPlaylistActivity;
import com.ytplayer.activity.search.YTSearchActivity;
import com.ytplayer.activity.single.YTPlayerActivity;
import com.ytplayer.activity.single.YTSlidingActivity;
import com.ytplayer.adapter.YTVideoModel;
import com.ytplayer.util.Logger;
import com.ytplayer.util.YTConstant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Created by Abhijit on 2/6/2018.
 */
public class YTUtility {

    public static void showKeyboard(View view, Context activity) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
//                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void openExternalYoutubeVideoPlayer(Activity activity, String googleApiKey, String youtubeVideoId) {
        try {
            activity.startActivity(YouTubeStandalonePlayer.createVideoIntent(activity, googleApiKey, youtubeVideoId, 0, true, false));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }

    public static void openExternalYoutubePlaylistPlayer(Activity activity, String googleApiKey, String playlistId) {
        try {
            activity.startActivity(YouTubeStandalonePlayer.createPlaylistIntent(activity, googleApiKey, playlistId, 0, 0, true, true));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }

    public static void openInternalYoutubePlayer(Context context) {
        try {
            context.startActivity(new Intent(context, YTPlayerActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }

    public static void openInternalYoutubePlayer(Context context, boolean isDetailVisible) {
        try {
            Intent intent = new Intent(context, YTSlidingActivity.class);
            intent.putExtra(YTConstant.VIDEO_DETAIL, isDetailVisible);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }

    public static void openInternalYoutubeSlidingPanel(Context context) {
        try {
            context.startActivity(new Intent(context, YTSlidingActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }

    public static void openInternalYoutubePlaylistPlayer(Context context, String playerName, ArrayList<YTVideoModel> playlist) {
        try {
            Intent intent = new Intent(context, YTPlaylistActivity.class);
            intent.putExtra(YTConstant.PLAYER_NAME, playerName);
            intent.putExtra(YTConstant.PLAYLIST, playlist);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }

    public static void openInternalYoutubeByPlaylistId(Context context, String playerName, String playListId) {
        try {
            Intent intent = new Intent(context, YTPlaylistActivity.class);
            intent.putExtra(YTConstant.PLAYER_NAME, playerName);
            intent.putExtra(YTConstant.PLAYLIST_ID, playListId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }

    public static void openInternalYoutubeByChannelId(Context context, String playerName, String channelId) {
        try {
            Intent intent = new Intent(context, YTChannelPlaylistActivity.class);
            intent.putExtra(YTConstant.PLAYER_NAME, playerName);
            intent.putExtra(YTConstant.CHANNEL_ID, channelId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }

    }

    /**
     * @param playerName Toolbar title name
     * @param playListIds Playlist ids in comma separated
     */
    public static void openInternalYoutubeByPlayListMultipleIds(Context context, String playerName, String playListIds) {
        try {
            Intent intent = new Intent(context, YTChannelPlaylistActivity.class);
            intent.putExtra(YTConstant.PLAYER_NAME, playerName);
            intent.putExtra(YTConstant.PLAYLIST_ID, playListIds);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }

    }

    public static void openInternalYoutubePlaylistPlayer(Context context, String channelId) {
        try {
            Intent intent = new Intent(context, YTSearchActivity.class);
            intent.putExtra(YTConstant.CHANNEL_ID, channelId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }


    public static int parseInt(String totalResults) {
        try {
            return Integer.parseInt(totalResults);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void log(String message) {
        if (YTConstant.isLogEnabled) {
            Log.d("@ytplayer", message);
        }
    }

    // input date is PT1H1M13S
    public static String getValidDuration(String youtubeDuration) {
        Calendar c = new GregorianCalendar();
        try {
            DateFormat df = new SimpleDateFormat("'PT'mm'M'ss'S'", Locale.US);
            Date d = df.parse(youtubeDuration);
            c.setTime(d);
        } catch (ParseException e) {
            try {
                DateFormat df = new SimpleDateFormat("'PT'hh'H'mm'M'ss'S'", Locale.US);
                Date d = df.parse(youtubeDuration);
                c.setTime(d);
            } catch (ParseException e1) {
                try {
                    DateFormat df = new SimpleDateFormat("'PT'ss'S'", Locale.US);
                    Date d = df.parse(youtubeDuration);
                    c.setTime(d);
                } catch (ParseException e2) {
                }
            }
        }
        c.setTimeZone(TimeZone.getDefault());

        String time = "";
        if ( c.get(Calendar.HOUR) > 0 ) {
            if ( String.valueOf(c.get(Calendar.HOUR)).length() == 1 ) {
                time += "0" + c.get(Calendar.HOUR);
            }
            else {
                time += c.get(Calendar.HOUR);
            }
            time += ":";
        }
        // test minute
        if ( String.valueOf(c.get(Calendar.MINUTE)).length() == 1 ) {
            time += "0" + c.get(Calendar.MINUTE);
        }
        else {
            time += c.get(Calendar.MINUTE);
        }
        time += ":";
        // test second
        if ( String.valueOf(c.get(Calendar.SECOND)).length() == 1 ) {
            time += "0" + c.get(Calendar.SECOND);
        }
        else {
            time += c.get(Calendar.SECOND);
        }
        return time ;
    }

    public static void openInternalYoutubeSubscribe(Context context, String youtubeChannelId) {
        // Contact Developer to update this option
        // UnComment SubscribeActivity for use working Class
//        context.startActivity(new Intent(context, SubscribeActivity.class)
//                .putExtra(YTConstant.CHANNEL_ID, youtubeChannelId));
        // uncomment Core Library on gradle file
        Toast.makeText(context,"Contact Developer to update this option", Toast.LENGTH_SHORT).show();
    }
}
