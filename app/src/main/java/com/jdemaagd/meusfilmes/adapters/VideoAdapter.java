package com.jdemaagd.meusfilmes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.jdemaagd.meusfilmes.R;
import com.jdemaagd.meusfilmes.models.api.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private static final String LOG_TAG = VideoAdapter.class.getSimpleName();

    private List<Video> mVideos;

    private final VideoAdapter.VideoAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages
     */
    public interface VideoAdapterOnClickHandler {
        void onClick(Video video);
    }

    public VideoAdapter(VideoAdapter.VideoAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of children views for a video
     */
    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // final ImageView mPosterImageView;

        public VideoViewHolder(View view) {
            super(view);
            // mPosterImageView = view.findViewById(R.id.iv_poster);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Video video = mVideos.get(adapterPosition);
            mClickHandler.onClick(video);
        }
    }

    /**
     * This gets called when each new ViewHolder is created
     * This happens when the RecyclerView is laid out
     * Enough ViewHolders will be created to fill the screen and allow for scrolling
     * @param viewGroup The ViewGroup that these ViewHolders are contained within
     * @param viewType  use this viewType integer to provide a different layout
     * @return A new VideoViewHolder that holds the View for each list item
     */
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.item_movie;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new VideoAdapter.VideoViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by RecyclerView to display data at specified position
     * Updates contents of ViewHolder to display video details for this particular position
     * @param videoViewHolder ViewHolder which should be updated to represent contents of item at given position
     * @param position position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder videoViewHolder, int position) {
        Video video = mVideos.get(position);

        Log.d(LOG_TAG, "Load Video.");
        // TODO: bind to Video items
    }

    /**
     * This returns the number of items to display
     * It is used behind the scenes to help layout Views and for animations
     * @return The number of videos
     */
    @Override
    public int getItemCount() {
        if (null == mVideos) return 0;
        return mVideos.size();
    }

    /**
     * This method is used to set videos on a VideoAdapter if already created
     * This is handy when getting new data from the web
     *   but don't want to create a new VideoAdapter to display it
     * @param videos The new videos to be displayed
     */
    public void setVideos(List<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }
}
