package com.jdemaagd.meusfilmes.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

// TODO: separate models for API and database

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int mMovieId;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @ColumnInfo(name = "duration")
    @SerializedName("runtime")
    private int mDuration;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String mOriginalTitle;

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private double mPopularity;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String mPosterPath;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String mReleaseDate;

    @ColumnInfo(name = "synopsis")
    @SerializedName("overview")
    private String mSynopsis;

    @ColumnInfo(name = "user_rating")
    @SerializedName("vote_average")
    private double mUserRating;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int mVoteCount;

    @Ignore
    public Movie(int movieId, String backdropPath, String posterPath) {
        this.mMovieId = movieId;
        this.mBackdropPath = backdropPath;
        this.mPosterPath = posterPath;
    }

    public Movie(int movieId, String backdropPath, int duration, String originalTitle, double popularity,
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
