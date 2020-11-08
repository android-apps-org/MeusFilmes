package com.jdemaagd.meusfilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jdemaagd.meusfilmes.adapters.MovieAdapter;
import com.jdemaagd.meusfilmes.adapters.MovieAdapter.MovieAdapterOnClickHandler;
import com.jdemaagd.meusfilmes.data.AppDatabase;
import com.jdemaagd.meusfilmes.data.AppSettings;
import com.jdemaagd.meusfilmes.databinding.ActivityMovieBinding;
import com.jdemaagd.meusfilmes.decorator.GridItemDecorator;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.network.JsonUtils;
import com.jdemaagd.meusfilmes.network.UrlUtils;
import com.jdemaagd.meusfilmes.viewmodels.MovieViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MovieActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    private static final String LOG_TAG = MovieActivity.class.getSimpleName();

    private static final String SORT_FAVORITE = "favorite";
    private static final String SORT_POPULAR = "popular";
    private static final String SORT_TOP_RATED = "top_rated";

    private ActivityMovieBinding mBinding;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovies;
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

        setupViewModel();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            String sortDescriptor = params[0];

            try {
                URL moviesRequestUrl = UrlUtils.buildMoviesUrl(sortDescriptor);
                List<Movie> movies = JsonUtils.getMoviesFromJson(UrlUtils.getResponseFromRequestUrl(moviesRequestUrl));

                return movies;
            } catch (Exception e) {
                Log.v(LOG_TAG, "ERROR: Fetching Movie Posters... ");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movies != null) {
                showMovies();
                mMovieAdapter.setPosters(movies);
            } else {
                Log.v(LOG_TAG, "No Movie Posters to show... ");
                showErrorMessage();
            }
        }
    }

    /**
     * This method is for responding to clicks from our list
     * @param movie String describing weather details for a particular day
     */
    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Intent movieIntent = new Intent(context, MovieDetailsActivity.class);

        Bundle bundle = new Bundle();
        movieIntent.putExtra("MOVIE_ID", movie.getMovieId());

        movieIntent.putExtras(bundle);
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

        if (id == R.id.action_favorites && !mSortDescriptor.equals(SORT_FAVORITE)) {
            mSortDescriptor = SORT_FAVORITE;
            loadMovies();
            return true;
        }

        if (id == R.id.action_popular && !mSortDescriptor.equals(SORT_POPULAR)) {
            mSortDescriptor = SORT_POPULAR;
            loadMovies();
            return true;
        }

        if (id == R.id.action_top_rated && !mSortDescriptor.equals(SORT_TOP_RATED)) {
            mSortDescriptor = SORT_TOP_RATED;
            loadMovies();
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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // this.getDisplay().getRealMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            if (width > 1000) {
                return 3;
            } else {
                return 2;
            }
        } else {
            if (width > 1700) {
                return 5;
            } else if (width > 1200) {
                return 4;
            } else {
                return 3;
            }
        }
    }

    private void loadMovies() {
        if (mSortDescriptor.equals(SORT_FAVORITE)) {
            AppDatabase appDb = AppDatabase.getInstance(MovieActivity.this);
            final LiveData<List<Movie>> movies = appDb.movieDao().getMovies();
            movies.observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movies) {
                    Log.d(LOG_TAG, "Receiving database update from LiveData.");
                    mMovieAdapter.setPosters(movies);
                }
            });
        } else {
            new FetchMoviesTask().execute(mSortDescriptor);
        }
    }

    private void setupViewModel() {
        MovieViewModel movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getMovies().observe(this, favMovies -> {
            if(favMovies.size() > 0) {
                mMovies.clear();
                mMovies = favMovies;
            }
            for (int i = 0; i < mMovies.size(); i++) {
                Log.d(LOG_TAG, "Favorite Movie: " + mMovies.get(i).getOriginalTitle());
            }
            loadMovies();
        });
    }

    /**
     * This method will make the error message visible and hide the weather View
     * Since it is okay to redundantly set the visibility of a View,
     *   we don't need to check whether each view is currently visible or invisible
     */
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the View for movies visible and hide the error message
     * Since it is okay to redundantly set the visibility of a View,
     *   we don't need to check whether each view is currently visible or invisible
     */
    private void showMovies() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
