package com.jdemaagd.meusfilmes.models;

import java.io.Serializable;

public class Movie implements Serializable {

    // "w92", "w154", "w185", "w342", "w500", "w780",
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";

    private int mMovieId;
    private String mBackdropPath;
    private int mDuration;
    private double mPopularity;
    private String mPosterPath;
    private long mReleaseDate;
    private String mSynopsis;
    private double mUserRating;
    private int mVoteCount;

    public Movie(int movieId, String backdropPath, String posterPath){
        this.mMovieId = movieId;
        this.mBackdropPath = backdropPath;
        this.mPosterPath = posterPath;
    }

    public Movie(int movieId, String backdropPath, int duration, double popularity, String posterPath, long releaseDate, String synopsis, double userRating, int voteCount){
        this.mMovieId = movieId;
        this.mBackdropPath = backdropPath;
        this.mDuration = duration;
        this.mPopularity = popularity;
        this.mPosterPath = posterPath;
        this.mReleaseDate = releaseDate;
        this.mSynopsis = synopsis;
        this.mUserRating = userRating;
        this.mVoteCount = voteCount;
    }

    public int getMovieId() { return mMovieId; }

    public String getPosterUrl() {
        return POSTER_BASE_URL + mPosterPath;
    }
}
