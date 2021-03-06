package com.ytplayer.activity.single;

import android.os.Bundle;
import android.util.Log;

import com.slidinguppanel.SlidingUpPanelLayout;
import com.ytplayer.R;
import com.ytplayer.activity.YTBaseActivity;
import com.ytplayer.util.YTConfig;

/**
 * @author Created by Abhijit on 25/06/2018.
 */
public class YTSlidingActivity extends YTBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yt_activity_youtube_sliding);
    }


    @Override
    public void initYTPlayer() {
        dragView = findViewById(R.id.dragView);
        slidingLayout = findViewById(R.id.sliding_layout);
        playerView = findViewById(R.id.youtubePlayerView);
        scrollView = findViewById(R.id.scroll_view_video_detail);
    }

    @Override
    public void onInitializationSuccess() {
        playVideo(YTConfig.getVideoId());

    }

    @Override
    public void onPanelStateChanged(SlidingUpPanelLayout.PanelState newState) {
        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            Log.d("@onPanelStateChanged", "EXPANDED");

        } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            Log.d("@onPanelStateChanged", "COLLAPSED");
            finish();
        }
    }
}
