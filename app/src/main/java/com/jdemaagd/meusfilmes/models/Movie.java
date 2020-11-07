package com.jdemaagd.meusfilmes.models;

import java.io.Serializable;

public class Movie implements Serializable {

    // "w92", "w154", "w185", "w342", "w500", "w780",
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";

    private int mMovieId;
    private String mBackdropPath;
    private int mDuration;
    private String mOriginalTitle;
    private double mPopularity;
    private String mPosterPath;
    private String mReleaseDate;
    private String mSynopsis;
    private double mUserRating;
    private int mVoteCount;

    public Movie(int movieId, String backdropPath, double popularity, String posterPath, double voteAverage){
        this.mMovieId = movieId;
        this.mBackdropPath = backdropPath;
        this.mPopularity = popularity;
        this.mPosterPath = posterPath;
        this.mUserRating = voteAverage;
    }

    public Movie(int movieId, String backdropPath, int duration, String originalTitle, double popularity, String posterPath, String releaseDate, String synopsis, double userRating, int voteCount){
        this.mMovieId = movieId;
        this.mBackdropPath = backdropPath;
        this.mDuration = duration;
        this.mOriginalTitle = originalTitle;
        this.mPopularity = popularity;
        this.mPosterPath = posterPath;
        this.mReleaseDate = releaseDate;
        this.mSynopsis = synopsis;
        this.mUserRating = userRating;
        this.mVoteCount = voteCount;
    }

    public String getDuration() { return Integer.toString(mDuration); }

    public int getMovieId() { return mMovieId; }

    public String getOriginalTitle() { return mOriginalTitle; }

    public String getOverview() { return mSynopsis; }

    public String getPosterUrl() {
        return POSTER_BASE_URL + mPosterPath;
    }

    public String getReleaseYear() { return mReleaseDate.substring(0, 4); }

    public String getVoteAverage() { return mUserRating + " / 10"; }

}
