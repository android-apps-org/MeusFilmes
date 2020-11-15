package com.jdemaagd.meusfilmes.network;

import com.jdemaagd.meusfilmes.models.ApiResponse;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.models.api.Review;
import com.jdemaagd.meusfilmes.models.api.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBClient {

    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") int id);

    @GET("movie/popular")
    Call<ApiResponse<Movie>> getPopularMovies(@Query("language") String language,
                                                    @Query("page") String page);

    @GET("movie/{id}/reviews")
    Call<ApiResponse<Review>> getReviews(@Path("id") String id);

    @GET("movie/top_rated")
    Call<ApiResponse<Movie>> getTopRatedMovies(@Query("language") String language,
                                                     @Query("page") String page);

    @GET("movie/{id}/videos")
    Call<ApiResponse<Video>> getVideos(@Path("id") String id);
}
