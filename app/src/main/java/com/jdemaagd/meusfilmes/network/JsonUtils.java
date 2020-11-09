package com.jdemaagd.meusfilmes.network;

import com.jdemaagd.meusfilmes.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Note: final modifier since class does not extend any other classes
public final class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    private static final String MOVIE_ID = "id";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String POSTER_PATH  = "poster_path";

    public static List<Movie> getMoviesFromJson(String moviesJson) throws JSONException {

        final String MOVIES = "results";

        JSONObject movies = new JSONObject(moviesJson);
        JSONArray moviesArray = movies.getJSONArray(MOVIES);

        List<Movie> parsedMovies = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject jsonMovie = moviesArray.getJSONObject(i);

            int movieId = jsonMovie.getInt(MOVIE_ID);
            String posterPath = jsonMovie.getString(POSTER_PATH);
            String backdropPath = jsonMovie.getString(BACKDROP_PATH);

            Movie movie = new Movie(movieId, backdropPath, false, posterPath);

            parsedMovies.add(movie);
        }

        return parsedMovies;
    }

    public static Movie getMovieFromJson(String movieJson) throws JSONException {

        final String DURATION = "runtime";
        final String ORIGINAL_TITLE = "original_title";
        final String POPULARITY = "popularity";
        final String RELEASE_DATE = "release_date";
        final String SYNOPSIS = "overview";
        final String USER_RATING = "vote_average";
        final String VOTE_COUNT = "vote_count";

        JSONObject movieObj = new JSONObject(movieJson);
        int movieId = movieObj.getInt(MOVIE_ID);
        String backdropPath = movieObj.getString(BACKDROP_PATH);
        int duration = movieObj.getInt(DURATION);
        String originalTitle = movieObj.getString(ORIGINAL_TITLE);
        double popularity = movieObj.getDouble(POPULARITY);
        String posterPath = movieObj.getString(POSTER_PATH);
        String releaseDate = movieObj.getString(RELEASE_DATE);
        String synopsis = movieObj.getString(SYNOPSIS);
        double userRating = movieObj.getDouble(USER_RATING);
        int voteCount = movieObj.getInt(VOTE_COUNT);

        boolean isFavorite = false;

        return new Movie(movieId, backdropPath, duration, isFavorite, originalTitle,
                popularity, posterPath, releaseDate, synopsis, userRating, voteCount);
    }

    private static Movie getFavoriteMovie(List<Movie> favMovies, int movieId) {

        for (Movie movie : favMovies) {
            if (movie.getMovieId() == movieId)
                return movie;
        }

        return null;
    }
}
