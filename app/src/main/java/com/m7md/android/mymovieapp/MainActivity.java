package com.m7md.android.mymovieapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends SingleFragmentActivity implements MainFragment.Callbacks{


    @Override
    public Fragment CreateFragment() {
        return new MainFragment();
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Movie movie) {
            if (findViewById(R.id.detail_fragment_container) == null) {
                Intent intent = DetailActivity.setIntent(this,movie);
                startActivity(intent);
            } else {
                Fragment newDetail = DetailFragment.newInstance(movie);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, newDetail)
                        .commit();
            }
    }
}