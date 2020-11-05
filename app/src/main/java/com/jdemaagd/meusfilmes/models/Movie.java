package com.jdemaagd.meusfilmes.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int movieId;

    @ColumnInfo(name = "backdrop_path")
    public String backdropPath;

    @ColumnInfo(name = "runtime")
    public int duration;

    public double popularity;

    @ColumnInfo(name = "poster_path")
    public String posterPath;

    @ColumnInfo(name = "release_date")
    public long releaseDate;

    @ColumnInfo(name = "overview")
    public String synopsis;

    @ColumnInfo(name = "vote_average")
    public double userRating;

    @ColumnInfo(name = "vote_count")
    public int voteCount;


    // Constructor for MainActivity to display list of movies via poster
    @Ignore
    public Movie(int movieId, String backdropPath, String posterPath){
        this.movieId = movieId;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
    }

    // Constructor for Room/MovieDetailsActivity
    public Movie(int movieId, String backdropPath, int duration, double popularity, String posterPath, long releaseDate, String synopsis, double userRating, int voteCount){
        this.movieId = movieId;
        this.backdropPath = backdropPath;
        this.duration = duration;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.voteCount = voteCount;
    }

    public int getMovieId() { return movieId; }
    public String getPosterPath() { return posterPath; }

}
