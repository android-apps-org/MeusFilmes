package com.jdemaagd.meusfilmes.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.jdemaagd.meusfilmes.R;
import com.jdemaagd.meusfilmes.databinding.ActivityReviewMovieBinding;
import com.jdemaagd.meusfilmes.models.api.Review;

public class MovieReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReviewMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_review_movie);

        Intent intent = getIntent();
        Review review = intent.getParcelableExtra(getString(R.string.intent_extra_review));
        String movieTitle = intent.getStringExtra(getString(R.string.intent_extra_movie_title));
        int color = intent.getIntExtra(getString(R.string.intent_extra_color_actionbar),
                ContextCompat.getColor(this, R.color.purple_700));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(String.format("%s - %s", getString(R.string.review), movieTitle));
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }

        binding.setReview(review);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
