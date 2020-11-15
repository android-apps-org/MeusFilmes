package com.jdemaagd.meusfilmes.models.api;

public class Video {

    private String mKey;
    private String mName;
    private int mSize;
    private String mType;
    private String mVideoId;

    public Video(String key, String name, int size, String type, String videoId) {
        mKey = key;
        mName = name;
        mSize = size;
        mType = type;
        mVideoId = videoId;
    }

    public String getKey() { return mKey; }

    public String getName() { return mName; }

    public int getSize() { return mSize; }

    public String getType() { return mType; }

    public String getVideoId() { return mVideoId; }
}
