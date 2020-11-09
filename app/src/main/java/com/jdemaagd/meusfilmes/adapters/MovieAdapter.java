package com.jdemaagd.meusfilmes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.jdemaagd.meusfilmes.R;
import com.jdemaagd.meusfilmes.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    // "w92", "w154", "w185", "w342", "w500", "w780",
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";

    private List<Movie> mMovies;

    /*
     * An on-click handler to make it easy for an Activity to interface with RecyclerView
     */
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /**
     * Creates a MovieAdapter
     * @param clickHandler on-click handler for this adapter
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of children views for a movie
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mPosterImageView;

        public MovieViewHolder(View view) {
            super(view);
            mPosterImageView = view.findViewById(R.id.iv_poster);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    /**
     * This gets called when each new ViewHolder is created
     * This happens when the RecyclerView is laid out
     * Enough ViewHolders will be created to fill the screen and allow for scrolling
     * @param viewGroup The ViewGroup that these ViewHolders are contained within
     * @param viewType  use this viewType integer to provide a different layout
     * @return A new MovieViewHolder that holds the View for each list item
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.movie_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new MovieViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by RecyclerView to display data at specified position
     * Updates contents of ViewHolder to display movie details for this particular position
     * @param movieViewHolder ViewHolder which should be updated to represent contents of item at given position
     * @param position position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.get()
                .load(POSTER_BASE_URL + movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(movieViewHolder.mPosterImageView);
    }

    /**
     * This returns the number of items to display
     * It is used behind the scenes to help layout Views and for animations
     * @return The number of items available in movies
     */
    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    /**
     * This method is used to set movies on a MovieAdapter if already created
     * This is handy when getting new data from the web
     *   but don't want to create a new MovieAdapter to display it
     * @param movies The new movies to be displayed
     */
    public void setPosters(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
