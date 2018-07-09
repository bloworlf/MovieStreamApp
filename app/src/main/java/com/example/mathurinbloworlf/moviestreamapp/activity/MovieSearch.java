package com.example.mathurinbloworlf.moviestreamapp.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.adapter.MovieAdapter;
import com.example.mathurinbloworlf.moviestreamapp.constants.MovieDB;
import com.example.mathurinbloworlf.moviestreamapp.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieSearch extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieAdapter movieAdapter;
    ListView listView;
    String title;
    private int page = 1;
    //String url = "https://api.themoviedb.org/3/search/movie?api_key="+ MovieDB.key+ "&query=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        Toolbar toolbar = findViewById(R.id.movie_search_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MovieFeedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView = findViewById(R.id.info_listview);
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movies);
        listView.setAdapter(movieAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //
                return false;
            }
        });

        /*
        if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() -1 &&
                listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
            //It is scrolled all the way down here
            page++;
            loadMovie(intent.getStringExtra("title"), page);
        }
        */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                        listView.getFooterViewsCount()) >= (movieAdapter.getCount() - 1)) {
                    // Now your listview has hit the bottom
                    page++;
                    loadMovie(title, page);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        loadMovie(title, page);
        setTitle("Result for : "+title);

    }

    private void loadMovie(String title, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.themoviedb.org/3/search/movie?api_key="+ MovieDB.key+ "&query="+title+"&page="+page,
                new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieArray;

                try {
                    movieArray = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieArray));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
