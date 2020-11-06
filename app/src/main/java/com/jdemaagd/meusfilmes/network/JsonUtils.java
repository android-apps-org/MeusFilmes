package com.jdemaagd.meusfilmes.network;

import com.jdemaagd.meusfilmes.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Note: final modifier since class does not extend any other classes
public final class JsonUtils {

    public static List<Movie> getMoviesFromJson(String moviesJson) throws JSONException {

        final String MOVIE_ID = "id";
        final String MOVIES = "results";

        final String POSTER_PATH  = "poster_path";
        final String BACKDROP_PATH = "backdrop_path";

        JSONObject movies = new JSONObject(moviesJson);
        JSONArray moviesArray = movies.getJSONArray(MOVIES);

        List<Movie> parsedMovies = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject jsonMovie = moviesArray.getJSONObject(i);

            int movieId = jsonMovie.getInt(MOVIE_ID);
            String posterPath = jsonMovie.getString(POSTER_PATH);
            String backdropPath = jsonMovie.getString(BACKDROP_PATH);

            Movie movie = new Movie(movieId, posterPath, backdropPath);

            parsedMovies.add(movie);
        }

        return parsedMovies;
    }
}
