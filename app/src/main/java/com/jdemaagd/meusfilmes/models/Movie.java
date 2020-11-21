package com.jdemaagd.meusfilmes.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

// TODO: separate models for API and database (doing too much!)

@Entity(tableName = "movie")
public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public Movie(int movieId, String backdropPath, int duration, String originalTitle, double popularity, String posterPath,
                 String releaseDate, String synopsis, double userRating, int voteCount) {
        mMovieId = movieId;
        mBackdropPath = backdropPath;
        mDuration = duration;
        mOriginalTitle = originalTitle;
        mPopularity = popularity;
        mPosterPath = posterPath;
        mReleaseDate = releaseDate;
        mSynopsis = synopsis;
        mUserRating = userRating;
        mVoteCount = voteCount;
    }

    @Ignore
    private Movie(Parcel in) {
        mMovieId = in.readInt();
        mBackdropPath = in.readString();
        mOriginalTitle = in.readString();
        mPopularity = in.readDouble();
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mSynopsis = in.readString();
        mUserRating = in.readDouble();
        mVoteCount = in.readInt();
    }

    public String getBackdropPath() { return mBackdropPath; }

    public int getDuration() { return mDuration; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getFormattedDate() {
        LocalDate localDate = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(mReleaseDate));
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(localDate);
    }

    public int getMovieId() { return mMovieId; }

    public String getOriginalTitle() { return mOriginalTitle; }

    public double getPopularity() { return mPopularity; }

    public String getPosterPath() { return mPosterPath; }

    public String getReleaseDate() { return mReleaseDate; }

    public String getSynopsis() { return mSynopsis; }

    public double getUserRating() { return mUserRating; }

    public int getVoteCount() { return mVoteCount; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMovieId);
        dest.writeString(mBackdropPath);
        dest.writeString(mOriginalTitle);
        dest.writeDouble(mPopularity);
        dest.writeString(mPosterPath);
        dest.writeString(mReleaseDate);
        dest.writeString(mSynopsis);
        dest.writeDouble(mUserRating);
        dest.writeInt(mVoteCount);
    }
}
