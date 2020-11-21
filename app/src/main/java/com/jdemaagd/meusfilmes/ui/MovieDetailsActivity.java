package com.jdemaagd.meusfilmes.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jdemaagd.meusfilmes.R;
import com.jdemaagd.meusfilmes.adapters.ReviewAdapter;
import com.jdemaagd.meusfilmes.adapters.VideoAdapter;
import com.jdemaagd.meusfilmes.common.AppConstants;
import com.jdemaagd.meusfilmes.common.AppDatabase;
import com.jdemaagd.meusfilmes.common.AppExecutor;
import com.jdemaagd.meusfilmes.common.AppSettings;
import com.jdemaagd.meusfilmes.databinding.ActivityDetailsMovieBinding;
import com.jdemaagd.meusfilmes.decorators.HorizontalItemDecorator;
import com.jdemaagd.meusfilmes.models.ApiResponse;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.models.api.Review;
import com.jdemaagd.meusfilmes.models.api.Video;
import com.jdemaagd.meusfilmes.network.TMDBClient;
import com.jdemaagd.meusfilmes.network.TMDBService;
import com.jdemaagd.meusfilmes.viewmodels.MovieDetailsViewModel;
import com.jdemaagd.meusfilmes.viewmodels.ViewModelFactory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    private TMDBClient mApiClient;
    private AppDatabase mAppDatabase;
    private AppExecutor mAppExecutor;
    private ActivityDetailsMovieBinding mBinding;
    private int mColor;
    private boolean mIsFav;
    private Movie mMovie;
    private int mMovieNumber;
    private Target mTargetBackdrop;

    private ReviewAdapter mReviewAdapter;
    private VideoAdapter mVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_movie);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;
            mBinding.collapsingToolbar.getLayoutParams().height = (int) Math.round(width / 1.5);
        }

        Intent intent = getIntent();

        if (intent != null) {
            mAppExecutor = AppExecutor.getInstance();
            mApiClient = TMDBService.createService(TMDBClient.class);
            mAppDatabase = AppDatabase.getInstance(getApplicationContext());
            mMovie = intent.getParcelableExtra(getString(R.string.intent_extra_movie));
            mMovieNumber = intent.getIntExtra(getString(R.string.intent_extra_movie_number), -1);

            mBinding.setMovie(mMovie);
            mBinding.setPresenter(this);

//            setSupportActionBar(mBinding.toolbar);
//
//            if (getSupportActionBar() != null) {
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            }

            mIsFav = false;

            loadMovie();
            loadVideos(savedInstanceState);
            loadReviews(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.bundle_videos), mVideoAdapter.getList());
        outState.putParcelableArrayList(getString(R.string.bundle_reviews), mReviewAdapter.getList());
    }

    public void onClickFavoriteButton() {
        AppSettings.setChangedMovie(this, mMovieNumber);

        String snackBarText;
        if (mIsFav) {
            mAppExecutor.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mAppDatabase.movieDao().removeMovie(mMovie);
                }
            });
            mIsFav = false;
            mBinding.favBtn.setImageResource(R.drawable.ic_heart_border_pink_24dp);
            snackBarText = getString(R.string.remove_favorite);
        } else {
            mAppExecutor.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mAppDatabase.movieDao().addMovie(mMovie);
                }
            });
            mIsFav = true;
            mBinding.favBtn.setImageResource(R.drawable.ic_heart_pink_24dp);
            snackBarText = getString(R.string.add_favorite);
        }
        Snackbar.make(mBinding.coordinatorLayout, snackBarText, Snackbar.LENGTH_SHORT).show();
    }

    private void loadMovie() {
        if (mMovie != null) {

            mTargetBackdrop = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mBinding.backdrop.setImageBitmap(bitmap);
                    Palette.from(bitmap).generate(palette -> {
                        mColor = palette.getMutedColor(R.attr.colorPrimary) | 0xFF000000;
                        mBinding.collapsingToolbar.setContentScrimColor(mColor);
                        mBinding.collapsingToolbar.setStatusBarScrimColor(mColor);
                    });
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Picasso.get()
                    .load(AppConstants.BACKDROP_BASE_URL + mMovie.getBackdropPath())
                    .into(mTargetBackdrop);

            mBinding.movieDetails.tvErrorMessage.setText(mMovie.getSynopsis());
            mBinding.movieDetails.tvOriginalTitle.setText(mMovie.getOriginalTitle());
            mBinding.movieDetails.tvReleaseDate.setText(mMovie.getFormattedDate());
            mBinding.movieDetails.tvVoteAverage.setText(mMovie.getUserRating() + " / 10");
            mBinding.movieDetails.tvOverview.setText(mMovie.getSynopsis());

            Picasso.get()
                    .load(AppConstants.POSTER_URL + mMovie.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(mBinding.movieDetails.ivPoster);

            mBinding.favBtn.setImageResource(R.drawable.ic_heart_border_pink_24dp);
            setFavIcon();
        } else {
            Log.d(LOG_TAG, getString(R.string.network_error));
            Toast.makeText(MovieDetailsActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            showErrorMessage();
        }
    }

    private void loadReviews(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.movieReviews.reviewsList.setLayoutManager(layoutManager);
        mBinding.movieReviews.reviewsList.setHasFixedSize(true);
        mBinding.movieReviews.reviewsList.setNestedScrollingEnabled(false);

        RecyclerView.ItemDecoration itemDecoration = new HorizontalItemDecorator(this);
        mBinding.movieReviews.reviewsList.addItemDecoration(itemDecoration);

        mReviewAdapter = new ReviewAdapter(this);
        mBinding.movieReviews.reviewsList.setAdapter(mReviewAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.bundle_reviews))) {
            mReviewAdapter.addReviewsList(savedInstanceState.<Review>getParcelableArrayList(getString(R.string.bundle_reviews)));
        } else {
            Call<ApiResponse<Review>> call = mApiClient.getReviews(mMovie.getMovieId());

            call.enqueue(new Callback<ApiResponse<Review>>() {
                @Override
                public void onResponse(Call<ApiResponse<Review>> call,
                                       Response<ApiResponse<Review>> response) {
                    List<Review> result = response.body().results;
                    mReviewAdapter.addReviewsList(result);
                    if (result.size() == 0) {
                        mBinding.movieReviews.reviewsLabel.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Review>> call, Throwable t) {
                    Log.d(LOG_TAG, getString(R.string.network_error));
                    Toast.makeText(MovieDetailsActivity.this,
                            getString(R.string.network_error), Toast.LENGTH_LONG).show();
                    showErrorMessage();
                }
            });
        }
    }

    private void loadVideos(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.movieVideos.videosList.setLayoutManager(layoutManager);
        mBinding.movieVideos.videosList.setHasFixedSize(true);
        mBinding.movieVideos.videosList.setNestedScrollingEnabled(false);

        RecyclerView.ItemDecoration itemDecoration = new HorizontalItemDecorator(this);
        mBinding.movieVideos.videosList.addItemDecoration(itemDecoration);

        mVideoAdapter = new VideoAdapter(this);
        mBinding.movieVideos.videosList.setAdapter(mVideoAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.bundle_videos))) {
            mVideoAdapter.addVideosList(savedInstanceState.getParcelableArrayList(getString(R.string.bundle_videos)));
        } else {
            Call<ApiResponse<Video>> call = mApiClient.getVideos(mMovie.getMovieId());

            call.enqueue(new Callback<ApiResponse<Video>>() {
                @Override
                public void onResponse(Call<ApiResponse<Video>> call,
                                       Response<ApiResponse<Video>> response) {
                    List<Video> result = response.body().results;
                    mVideoAdapter.addVideosList(result);
                    if (result.size() == 0) {
                        mBinding.movieVideos.videosLabel.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Video>> call, Throwable t) {
                    Log.d(LOG_TAG, getString(R.string.network_error));
                    Toast.makeText(MovieDetailsActivity.this,
                            getString(R.string.network_error), Toast.LENGTH_LONG).show();
                    showErrorMessage();
                }
            });
        }
    }

    private void setFavIcon() {
        ViewModelFactory factory = new ViewModelFactory(mAppDatabase, mMovie.getMovieId());
        final MovieDetailsViewModel movieDetailsViewModel = new ViewModelProvider(this, factory).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.getMovie().observe(this, favMovie -> {
            // movieDetailsViewModel.getMovie().removeObserver(this);
            Log.d(LOG_TAG, "Receiving LiveData Update.");
            if (favMovie == null) {
                mIsFav = false;
                mBinding.favBtn.setImageResource(R.drawable.ic_heart_border_pink_24dp);
            } else {
                mIsFav = true;
                mBinding.favBtn.setImageResource(R.drawable.ic_heart_pink_24dp);
            }
        });
    }

    private void showErrorMessage() {
        mBinding.movieDetails.ivPoster.setVisibility(View.INVISIBLE);
        mBinding.movieDetails.tvOriginalTitle.setVisibility(View.INVISIBLE);
        mBinding.movieDetails.tvOverview.setVisibility(View.INVISIBLE);
        mBinding.movieDetails.tvReleaseDate.setVisibility(View.INVISIBLE);
        mBinding.movieDetails.tvVoteAverage.setVisibility(View.INVISIBLE);

        mBinding.movieDetails.tvErrorMessage.setVisibility(View.VISIBLE);
    }
}
