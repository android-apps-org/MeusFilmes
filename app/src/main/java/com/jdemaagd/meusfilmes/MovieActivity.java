package com.jdemaagd.meusfilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jdemaagd.meusfilmes.adapters.MovieAdapter;
import com.jdemaagd.meusfilmes.adapters.MovieAdapter.MovieAdapterOnClickHandler;
import com.jdemaagd.meusfilmes.data.AppSettings;
import com.jdemaagd.meusfilmes.databinding.ActivityMovieBinding;
import com.jdemaagd.meusfilmes.decorators.GridItemDecorator;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MovieActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    private static final String LOG_TAG = MovieActivity.class.getSimpleName();
    private static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    private ActivityMovieBinding mBinding;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovies;
    private MovieViewModel mMovieViewModel;
    private RecyclerView mRecyclerView;
    private String mSortDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        mBinding.setLifecycleOwner(this);

        setViews();

        mMovies = new ArrayList<>();
        mSortDescriptor = AppSettings.getPopularitySortDescriptor(this);

        mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        loadMovies();
    }

    /**
     * This method is for responding to clicks from our list
     * @param movie String describing weather details for a particular day
     */
    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Intent movieIntent = new Intent(context, MovieDetailsActivity.class);
        movieIntent.putExtra(EXTRA_MOVIE_ID, movie.getMovieId());
        startActivity(movieIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        mMovies.clear();

        if (id == R.id.action_favorites) {
            String favoritesSort = AppSettings.getFavoritesSortDescriptor(this);
            if (!mSortDescriptor.equals(favoritesSort)) {
                mSortDescriptor = favoritesSort;
                loadMovies();
            }
            return true;
        }

        if (id == R.id.action_popular) {
            String popularSort = AppSettings.getPopularitySortDescriptor(this);
            if (!mSortDescriptor.equals(popularSort)) {
                mSortDescriptor = popularSort;
                loadMovies();
            }
            return true;
        }

        if (id == R.id.action_top_rated) {
            String topRatedSort = AppSettings.getTopRatedSortDescriptor(this);
            if (!mSortDescriptor.equals(topRatedSort)) {
                mSortDescriptor = topRatedSort;
                loadMovies();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViews() {
        mRecyclerView = mBinding.rvPosters;
        mErrorMessageDisplay = mBinding.tvErrorMessage;

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, getColumnCount());
        mRecyclerView.setLayoutManager(layoutManager);
        mBinding.rvPosters.addItemDecoration(new GridItemDecorator(this));

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        // mBinding.swipeRefreshLayout.setEnabled(false);
        mLoadingIndicator = mBinding.pbLoadingIndicator;
    }

    private int getColumnCount() {
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            return 3;
        } else {
            return 5;
        }
    }

    private void loadMovies() {
        if (mSortDescriptor.equals(AppSettings.getFavoritesSortDescriptor(this))) {
            mMovieViewModel.getFavMovies().observe(this, movies -> {
                Log.d(LOG_TAG, "Fetching favorite movies via LiveData in MovieViewModel.");
                if (movies == null) {
                    showErrorMessage();
                } else {
                    showMovies();
                }
                mMovieAdapter.setPosters(movies);
            });
        } else {
            mMovieViewModel.getSortedMovies(mSortDescriptor).observe(this, movies -> {
                Log.d(LOG_TAG, "Fetching TMDB API movies via RetroFit.");
                if (movies == null) {
                    showErrorMessage();
                } else {
                    showMovies();
                }
                mMovieAdapter.setPosters(movies);
            });
        }
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMovies() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
