package com.ytplayer.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.ytplayer.YTPlayer;
import com.ytplayer.util.Logger;


/**
 * @author Created by Abhijit on 2/6/2018.
 */
public class YTPlayerTextView extends AppCompatTextView {

    public YTPlayerTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public YTPlayerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public YTPlayerTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    public void applyCustomFont(Context context) {
        try {
            setTypeface(YTPlayer.getInstance().getTypeface());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }
    }

    public static void applyFont(TextView... textViews) {
        try {
            for (TextView textView : textViews){
                textView.setTypeface(YTPlayer.getInstance().getTypeface());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e.toString());
        }

    }
}
