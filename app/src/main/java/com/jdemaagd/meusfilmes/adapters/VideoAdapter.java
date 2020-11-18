package com.jdemaagd.meusfilmes.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdemaagd.meusfilmes.R;
import com.jdemaagd.meusfilmes.common.AppConstants;
import com.jdemaagd.meusfilmes.databinding.ItemVideoBinding;
import com.jdemaagd.meusfilmes.models.api.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final Context mContext;
    private List<Video> mList;

    public VideoAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ItemVideoBinding binding = ItemVideoBinding.inflate(layoutInflater, parent, false);
        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = mList.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public void addVideosList(List<Video> videosList) {
        mList = videosList;
        notifyDataSetChanged();
    }

    public ArrayList<Video> getList() {
        return (ArrayList<Video>) mList;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ItemVideoBinding binding;

        VideoViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Video video) {
            binding.setVideo(video);
            binding.setPresenter(this);

            String photoUrl = String.format(AppConstants.YT_PHOTO_URL, video.videoUrl);
            Picasso.get()
                    .load(photoUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(binding.ivVideo);
        }

        public void onClickVideo(String videoUrl) {
            Intent appIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(AppConstants.YT_INTENT_URI + videoUrl));

            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(AppConstants.YT_VIDEO_URL + videoUrl));
            try {
                mContext.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                mContext.startActivity(webIntent);
            }
        }
    }
}
