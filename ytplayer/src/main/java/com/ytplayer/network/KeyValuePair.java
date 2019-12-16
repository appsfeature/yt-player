package com.ytplayer.network;

/**
 * @author Created by Abhijit on 2/6/2018.
 */
public class KeyValuePair {
    private final String key;
    private final String value;

    public KeyValuePair(String key, String value) {
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
