package com.jdemaagd.meusfilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jdemaagd.meusfilmes.adapters.MovieAdapter;
import com.jdemaagd.meusfilmes.adapters.MovieAdapter.MovieAdapterOnClickHandler;
import com.jdemaagd.meusfilmes.models.Movie;
import com.jdemaagd.meusfilmes.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovies;
    private RecyclerView mRecyclerView;
    private MovieViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        bindViews();

        mMovies = new ArrayList<>();
        mViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // TODO: TMDB API Client Service 

        mViewModel.getMovies().observe(this, movies -> {
            mMovieAdapter.setData(movies);
            mMovieAdapter.notifyDataSetChanged();
        });
    }

    private void bindViews() {

        mRecyclerView = findViewById(R.id.rv_posters);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

    }

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
}