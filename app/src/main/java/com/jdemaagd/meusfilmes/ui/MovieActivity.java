package com.jdemaagd.meusfilmes.ui;

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
import android.widget.Toast;

import com.jdemaagd.meusfilmes.R;
import com.jdemaagd.meusfilmes.adapters.MovieAdapter;
import com.jdemaagd.meusfilmes.adapters.MovieAdapter.MovieAdapterOnClickHandler;
import com.jdemaagd.meusfilmes.common.AppConstants;
import com.jdemaagd.meusfilmes.common.AppSettings;
import com.jdemaagd.meusfilmes.common.ConnectivityCallback;
import com.jdemaagd.meusfilmes.databinding.ActivityMovieBinding;
import com.jdemaagd.meusfilmes.decorators.GridItemDecorator;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MovieActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    private static final String LOG_TAG = MovieActivity.class.getSimpleName();

    private ActivityMovieBinding mBinding;
    private TextView mErrorMessageDisplay;
    private GridLayoutManager mGridLayoutManager;
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

        ConnectivityCallback connectivityCallback = new ConnectivityCallback(getApplicationContext());
        connectivityCallback.registerNetworkCallback();
        Log.d(LOG_TAG, "Connected: " + AppConstants.IS_NETWORK_CONNECTED);

        mMovies = new ArrayList<>();
        mSortDescriptor = AppSettings.getPopularitySortDescriptor(this);

        setViews();

        loadMovies(1);
    }

    /**
     * This method is for responding to clicks from our list
     * @param movie String describing weather details for a particular day
     */
    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Intent movieIntent = new Intent(context, MovieDetailsActivity.class);
        movieIntent.putExtra(getString(R.string.extra_movie), movie);
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
                loadMovies(1);
            }
            return true;
        }

        if (id == R.id.action_popular) {
            String popularSort = AppSettings.getPopularitySortDescriptor(this);
            if (!mSortDescriptor.equals(popularSort)) {
                mSortDescriptor = popularSort;
                loadMovies(1);
            }
            return true;
        }

        if (id == R.id.action_top_rated) {
            String topRatedSort = AppSettings.getTopRatedSortDescriptor(this);
            if (!mSortDescriptor.equals(topRatedSort)) {
                mSortDescriptor = topRatedSort;
                loadMovies(1);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViews() {
        mRecyclerView = mBinding.rvPosters;
        mErrorMessageDisplay = mBinding.tvErrorMessage;

        mGridLayoutManager = new GridLayoutManager(this, getColumnCount());
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mBinding.rvPosters.addItemDecoration(new GridItemDecorator(this));

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        mBinding.swipeRefreshLayout.setEnabled(false);
        mBinding.swipeRefreshLayout.setOnRefreshListener(() -> loadMovies(1));

        mLoadingIndicator = mBinding.pbLoadingIndicator;
    }

    private int getColumnCount() {
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            return 3;
        } else {
            return 5;
        }
    }

    private void loadMovies(int page) {
        if(AppConstants.IS_NETWORK_CONNECTED)
        {
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
        else
        {
            Log.d(LOG_TAG, "MovieActivity.loadMovies: network error.");
            Toast.makeText(MovieActivity.this,
                    getString(R.string.network_error), Toast.LENGTH_LONG).show();
            showErrorMessage();
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
