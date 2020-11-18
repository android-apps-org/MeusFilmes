package com.jdemaagd.meusfilmes.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdemaagd.meusfilmes.databinding.ItemReviewBinding;
import com.jdemaagd.meusfilmes.models.api.Review;
import com.jdemaagd.meusfilmes.ui.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private final Activity mActivity;
    private List<Review> mReviews;

    public ReviewAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        ItemReviewBinding binding = ItemReviewBinding.inflate(layoutInflater, parent, false);
        return new ReviewAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        if (mReviews == null) {
            return 0;
        }
        return mReviews.size();
    }

    public void addReviewsList(List<Review> reviewsList) {
        mReviews = reviewsList;
        notifyDataSetChanged();
    }

    public ArrayList<Review> getList() {
        return (ArrayList<Review>) mReviews;
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemReviewBinding binding;

        ReviewAdapterViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Review review) {
            binding.setReview(review);
            binding.setPresenter((MovieDetailsActivity) mActivity);
        }
    }
}
