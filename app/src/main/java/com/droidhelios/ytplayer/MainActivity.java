package com.droidhelios.ytplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.droidhelios.ytplayer.util.AppPreferences;
import com.droidhelios.ytplayer.util.Constants;
import com.droidhelios.ytplayer.util.DeveloperKey;
import com.ytplayer.YTPlayer;
import com.ytplayer.adapter.YTVideoModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String YOUTUBE_VIDEO_ID = "KN5XtpD-jKw";
    static final String YOUTUBE_CHANNEL_ID = "UC_x5XG1OV2P6uZZ5FSM9Ttw";
    static final String YOUTUBE_PLAYLIST_ID = "RDHxNTDNJ7Ndo";
    static final String YOUTUBE_GOOGLE_PLAY_PLAYLIST_ID = "PLWz5rJ2EKKc_i9rV2WE-GhWLm4JmsraRM";
//    static final String YOUTUBE_MULTIPLE_PLAYLIST_IDS = "PLWz5rJ2EKKc_i9rV2WE-GhWLm4JmsraRM,PLWz5rJ2EKKc-lJo_RGGXL2Psr8vVCTWjM";
static final String YOUTUBE_MULTIPLE_PLAYLIST_IDS = "PLEI1TXdLd0MYJAKQlEihIJ1nrg-J_KYGm,PLWz5rJ2EKKc-lJo_RGGXL2Psr8vVCTWjM";
    private YTPlayer ytPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        (findViewById(R.id.btn_play_single1)).setOnClickListener(this);
        (findViewById(R.id.btn_play_single2)).setOnClickListener(this);
        (findViewById(R.id.btn_play_single3)).setOnClickListener(this);
        (findViewById(R.id.btn_open_playlist_by_video_ids)).setOnClickListener(this);
        (findViewById(R.id.btn_open_multiple_playlist_internal)).setOnClickListener(this);
        (findViewById(R.id.btn_open_playlist_internal)).setOnClickListener(this);
        (findViewById(R.id.btn_open_channel_internal)).setOnClickListener(this);
        (findViewById(R.id.btn_open_external)).setOnClickListener(this);
        (findViewById(R.id.btn_play_single4)).setOnClickListener(this);
        (findViewById(R.id.btn_open_subscribe)).setOnClickListener(this);

        ytPlayer = YTPlayer.getInstance(this, DeveloperKey.DEVELOPER_KEY)
                .setPlayerType(YTPlayer.VideoType.OPEN_INTERNAL_PLAYER)
                .maxListItemsCount(Constants.MAX_RESULTS_COUNT)
                .setTypeface(null);

        SwitchCompat switchCompat = findViewById(R.id.switchCompat);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            switchCompat.setChecked(true);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppPreferences.setDayMode(MainActivity.this, !isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_play_single1:
                ytPlayer.setPlayerType(YTPlayer.VideoType.OPEN_INTERNAL_PLAYER);
                ytPlayer.openVideo(YOUTUBE_VIDEO_ID);
                break;
            case R.id.btn_play_single3:
                ytPlayer.setPlayerType(YTPlayer.VideoType.OPEN_INTERNAL_SLIDING_PLAYER);
                ytPlayer.openVideo(YOUTUBE_VIDEO_ID, true);
                break;
            case R.id.btn_play_single4:
                ytPlayer.setPlayerType(YTPlayer.VideoType.OPEN_INTERNAL_SLIDING_PLAYER);
                ytPlayer.openSearch(YOUTUBE_CHANNEL_ID);
                break;
            case R.id.btn_open_playlist_internal:
                ytPlayer.openPlaylist("Playlist", YOUTUBE_GOOGLE_PLAY_PLAYLIST_ID);
                break;
            case R.id.btn_open_multiple_playlist_internal:
                ytPlayer.openPlaylistMultipleIds("Multiple Playlist", YOUTUBE_MULTIPLE_PLAYLIST_IDS);
                break;
            case R.id.btn_open_playlist_by_video_ids:
                ytPlayer.openViewPlaylist("Youtube", generateDummyVideoList());
                break;
            case R.id.btn_open_channel_internal:
                ytPlayer.openChannel("Channel", YOUTUBE_CHANNEL_ID);
                break;
            case R.id.btn_play_single2:
                ytPlayer.setPlayerType(YTPlayer.VideoType.OPEN_EXTERNAL);
                ytPlayer.openVideo(YOUTUBE_VIDEO_ID);
                break;
            case R.id.btn_open_external:
                ytPlayer.openPlaylistExternal(YOUTUBE_PLAYLIST_ID);
                break;
            case R.id.btn_open_subscribe:
                ytPlayer.openSubscribeOption(YOUTUBE_CHANNEL_ID);
                //old method
//                startActivity(new Intent(this, GoogleSignInActivity2.class));
                break;
            default:
        }
    }

    private ArrayList<YTVideoModel> generateDummyVideoList() {
        ArrayList<YTVideoModel> youtubeVideoModelArrayList = new ArrayList<>();

        //get the video id array, title array and duration array from strings.xml
        String[] videoIDArray = getResources().getStringArray(R.array.video_id_array);
        String[] videoTitleArray = getResources().getStringArray(R.array.video_title_array);
        String[] videoDurationArray = getResources().getStringArray(R.array.video_duration_array);

        //loop through all items and add them to arraylist
        for (int i = 0; i < videoIDArray.length; i++) {

            YTVideoModel playList = YTVideoModel.Builder()
                    .setVideoId(videoIDArray[i])
                    .setTitle(videoTitleArray[i])
                    .setDuration(videoDurationArray[i]);

            youtubeVideoModelArrayList.add(playList);

        }

        return youtubeVideoModelArrayList;
    }


}
