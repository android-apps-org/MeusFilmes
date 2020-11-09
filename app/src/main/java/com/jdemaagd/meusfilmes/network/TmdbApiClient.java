package com.jdemaagd.meusfilmes.network;

import com.jdemaagd.meusfilmes.models.ApiResponse;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.models.Review;
import com.jdemaagd.meusfilmes.models.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TmdbApiClient {

    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") String id);

    @GET("movie/popular")
    Call<ApiResponse<Movie>> getPopularMovies(String language, String page);

    @GET("movie/{id}/reviews")
    Call<ApiResponse<Review>> getReviews(@Path("id") String id);

    @GET("movie/top_rated")
    Call<ApiResponse<Movie>> getTopRatedMovies(String language, String page);

    @GET("movie/{id}/videos")
    Call<ApiResponse<Video>> getVideos(@Path("id") String id);
}
