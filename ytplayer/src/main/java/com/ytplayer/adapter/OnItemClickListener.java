package com.ytplayer.adapter;

import com.ytplayer.util.YTType;

/**
 * @author Created by Abhijit on 25/06/2018.
 */
public interface OnItemClickListener<T> {
    void onItemClick(T item, YTType ytType);
}
