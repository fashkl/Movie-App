package com.m7md.android.mymovieapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by m7md on 4/23/16.
 */
public class MainFragment extends android.support.v4.app.Fragment {
    GridView gridView;
    public static Callbacks mCallbacks;
    public interface Callbacks {
        void onCrimeSelected(Movie movie);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                taskFactory("popular");
                break;
            case R.id.top_rated:
                taskFactory("top_rated");
                break;
            case R.id.favourite:
                taskFactory("favourite");
                break;
            case R.id.menu_item_share:
                setShareIntent();
                break;
            default:
                taskFactory("popular");
        }
        return true;
    }

    void taskFactory(String type) {
        ArrayList<Movie> movies = null;



        ItemsTask itemsTask = new ItemsTask();
        itemsTask.setContext(getActivity().getBaseContext());


        itemsTask.execute(type, "");

        try {
            movies = itemsTask.get();
            Toast.makeText(getActivity(), movies.size()+"", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            Toast.makeText(getActivity().getBaseContext(), "" + e, Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            Toast.makeText(getActivity().getBaseContext(), "" + e, Toast.LENGTH_SHORT).show();
        }


        GridAdapter adapter = new GridAdapter(getActivity(), movies);
        Toast.makeText(getActivity(), movies.get(0).getID()+"", Toast.LENGTH_SHORT).show();
        gridView.setAdapter(adapter);

    }


    private void setShareIntent() {
        Cursor cursor = new MovieDB(getActivity()).selectMovie(-2);
        cursor.moveToFirst();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        /*
         "This A " + cursor.getString(cursor.getColumnIndex("title")) + " Movie Details \n" +
                "Overview :  " + cursor.getString(cursor.getColumnIndex("overview"));
         */
        String shareBody = "https://wwww.youtube.com/?v=" + cursor.getString(cursor.getColumnIndex("trailer"));
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Movie Url");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_movies,
                container, false);
        gridView=(GridView)view.findViewById(R.id.gridView);
        taskFactory("popular");
        return view;
    }
}
