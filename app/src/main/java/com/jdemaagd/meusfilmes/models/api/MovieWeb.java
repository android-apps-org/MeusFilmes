package com.jdemaagd.meusfilmes.models.api;

import com.google.gson.annotations.SerializedName;

// Note: API Movie
public class MovieWeb {

    @SerializedName("id")
    private int mMovieId;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @SerializedName("runtime")
    private int mDuration;

    @SerializedName("original_title")
    private String mOriginalTitle;

    @SerializedName("popularity")
    private double mPopularity;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("overview")
    private String mSynopsis;

    @SerializedName("vote_average")
    private double mUserRating;

    @SerializedName("vote_count")
    private int mVoteCount;

    public MovieWeb(int movieId, String backdropPath, String posterPath) {
        this.mMovieId = movieId;
        this.mBackdropPath = backdropPath;
        this.mPosterPath = posterPath;
    }

    public MovieWeb(int movieId, String backdropPath, int duration, String originalTitle, double popularity,
                 String posterPath, String releaseDate, String synopsis, double userRating, int voteCount) {
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

    public String getBackdropPath() { return mBackdropPath; }

    public int getDuration() { return mDuration; }

    public int getMovieId() { return mMovieId; }

    public String getOriginalTitle() { return mOriginalTitle; }

    public double getPopularity() { return mPopularity; }

    public String getPosterPath() { return mPosterPath; }

    public String getReleaseDate() { return mReleaseDate; }

    public String getSynopsis() { return mSynopsis; }

    public double getUserRating() { return mUserRating; }

    public int getVoteCount() { return mVoteCount; }
}
