package com.m7md.android.mymovieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


public class DetailActivity extends SingleFragmentActivity {

    public static final String MOVIE = "movie";
    private Movie movie;

    @Override
    public Fragment CreateFragment() {
        movie=getIntent().getParcelableExtra(MOVIE);
        return DetailFragment.newInstance(movie);
    }

    public static Intent setIntent(Context context, Movie movie) {

        Intent intent = new Intent(context, DetailActivity.class);

        intent.putExtra(MOVIE, movie);


        return intent;
    }


}
