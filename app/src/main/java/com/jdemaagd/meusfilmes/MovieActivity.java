package com.jdemaagd.meusfilmes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.jdemaagd.meusfilmes.adapters.MovieAdapter;
import com.jdemaagd.meusfilmes.adapters.MovieAdapter.MovieAdapterOnClickHandler;
import com.jdemaagd.meusfilmes.data.AppDatabase;
import com.jdemaagd.meusfilmes.data.AppSettings;
import com.jdemaagd.meusfilmes.databinding.ActivityMovieBinding;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.network.AppExecutor;
import com.jdemaagd.meusfilmes.network.JsonUtils;
import com.jdemaagd.meusfilmes.network.UrlUtils;

import java.net.URL;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MovieActivity extends AppCompatActivity implements
        MovieAdapterOnClickHandler, LoaderCallbacks<List<Movie>> {

    private static final String LOG_TAG = MovieActivity.class.getSimpleName();

    private AppDatabase mAppDatabase;
    private ActivityMovieBinding mBinding;
    private LoaderCallbacks<List<Movie>> mCallback;
    private Context mContext;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private String mSortDescriptor;

    private static final int MOVIES_LOADER_ID = 0;
    private static final String SORT_DESCRIPTOR = "Sort_Descriptor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        mBinding.setLifecycleOwner(this);

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

        mContext = MovieActivity.this;

        Stetho.initialize(
                Stetho.newInitializerBuilder(mContext)
                        .enableDumpapp(new DumperPluginsProvider() {
                            @Override
                            public Iterable<DumperPlugin> get() {
                                return null;
                            }
                        })
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(mContext))
                        .build());
        // TODO: network inspection ??

        setViews();

        mCallback = MovieActivity.this;

        mSortDescriptor = AppSettings.getTopRatedSortDescriptor(mContext);

        initLoader(mSortDescriptor);
    }

    /**
     * Instantiate and return a new Loader for the given ID
     * @param id The ID whose loader is to be created
     * @param loaderArgs Any arguments supplied by the caller
     * @return Return a new Loader instance that is ready to start loading
     */
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<List<Movie>>(this) {
            List<Movie> mMovies = null;

            @Override
            protected void onStartLoading() {
                if (mMovies != null) {
                    deliverResult(mMovies);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public List<Movie> loadInBackground() {

                String sortDescriptor = loaderArgs.getString(SORT_DESCRIPTOR);

                try {
                    URL moviesRequestUrl = UrlUtils.buildMoviesUrl(sortDescriptor);
                    return JsonUtils.getMoviesFromJson(UrlUtils.getResponseFromRequestUrl(moviesRequestUrl));

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(List<Movie> movies) {
                mMovies = movies;
                super.deliverResult(movies);
            }
        };
    }

    /**
     * Called when a previously created loader has finished its load
     * @param loader The Loader that has finished
     * @param movies The data generated by the Loader
     */
    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        Log.d(LOG_TAG, "Loading Movies from API or Database base on sort selector.");

        if (mSortDescriptor == "favorites") {
            Log.d(LOG_TAG, "Actively retrieving movies from database.");
            final LiveData<List<Movie>> favMovies = mAppDatabase.movieDao().getMovies();
            favMovies.observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    Log.d(LOG_TAG, "Receiving database update from LiveData.");
                    mMovieAdapter.setPosters(movies);
                }
            });
        } else {
            mMovieAdapter.setPosters(movies);
        }

        showMovies();
//        if (null == movies) {
//            showErrorMessage();
//        } else {
//            showMovies();
//        }
    }

    /**
     * Called when a previously created loader is being reset, and thus making its data unavailable
     * The application should at this point remove any references it has to the Loader's data
     * @param loader The Loader that is being reset
     */
    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

    /**
     * This method is used when we are resetting data,
     *   so that at one point in time during a refresh of our data,
     *   you can see that there is no data showing
     */
    private void invalidateData() {
        mMovieAdapter.setPosters(null);
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
        // bundle.putSerializable("MOVIE", movie);
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

        invalidateData();

        if (id == R.id.action_favorites) {
            mSortDescriptor = AppSettings.getFavoritesSortDescriptor(mContext);
            initLoader(mSortDescriptor);

            return true;
        }

        if (id == R.id.action_popular) {
            mSortDescriptor = AppSettings.getPopularitySortDescriptor(mContext);
            initLoader(mSortDescriptor);

            return true;
        }

        if (id == R.id.action_top_rated) {
            mSortDescriptor = AppSettings.getTopRatedSortDescriptor(mContext);
            initLoader(mSortDescriptor);

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
        mRecyclerView.setHasFixedSize(false);

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
                return 6;
            } else if (width > 1200) {
                return 5;
            } else {
                return 4;
            }
        }
    }

    private void initLoader(String sortDescriptor) {
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putString(SORT_DESCRIPTOR, sortDescriptor);
        LoaderManager.getInstance(this).initLoader(MOVIES_LOADER_ID, bundleForLoader, mCallback);
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
