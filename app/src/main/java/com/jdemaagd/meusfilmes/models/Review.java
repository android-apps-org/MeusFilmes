package com.jdemaagd.meusfilmes.models;

public class Review {

    private String mAuthor;
    private String mContent;
    private String mReviewId;
    private String mUrl;

    public Review(String author, String content, String reviewId, String url) {
        mAuthor = author;
        mContent = content;
        mReviewId = reviewId;
        mUrl = url;
    }

    public String getAuthor() { return mAuthor; }

    public String getContent() { return mContent; }

    public String getReviewId() { return mReviewId; }

    public String getUrl() { return mUrl; }
}
