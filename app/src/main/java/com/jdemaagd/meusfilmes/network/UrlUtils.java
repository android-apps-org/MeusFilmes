package com.jdemaagd.meusfilmes.network;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jdemaagd.meusfilmes.BuildConfig;

// Note: final modifier since class does not extend any other classes
public final class UrlUtils {

    private static final String LOG_TAG = UrlUtils.class.getSimpleName();

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3";

    final static String API_KEY = BuildConfig.TMDB_API_KEY;
    final static String API_PARAM = "api_key";
    final static String MOVIE_PATH_SEGMENT = "movie";

    public static URL buildMoviesUrl(String descriptor) {

        Uri.Builder uriBuilder = buildUpUri(descriptor);

        URL url = null;
        try {
            url = new URL(uriBuilder.toString());
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromRequestUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch(IOException e) {
            Log.v(LOG_TAG, "ERROR: Connecting... ");
            e.printStackTrace();
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }

    private static Uri.Builder buildUpUri(String descriptor) {

        List<String> pathSegments = buildUpUriPathSegments(descriptor);

        Uri builtUri = Uri.parse(MOVIES_BASE_URL);
        Uri.Builder uriBuilder = builtUri.buildUpon();

        for (String pathSegment : pathSegments)
            uriBuilder.appendPath(pathSegment);
        uriBuilder.appendQueryParameter(API_PARAM, API_KEY);
        uriBuilder.build();

        return uriBuilder;
    }

    private static List<String> buildUpUriPathSegments(String descriptor) {
        List<String> pathSegments = new ArrayList();

        pathSegments.add(MOVIE_PATH_SEGMENT);
        pathSegments.add(descriptor);

        return pathSegments;

        /*
            Reviews:
            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH_SEGMENT)
                .appendPath(movieId)
                .appendPath(REVIEWS_PATH_SEGMENT)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
         */

        /*
            Trailers:
            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(MOVIE_PATH_SEGMENT)
                .appendPath(movieId)
                .appendPath(VIDEOS_PATH_SEGMENT)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
         */

    }
}
