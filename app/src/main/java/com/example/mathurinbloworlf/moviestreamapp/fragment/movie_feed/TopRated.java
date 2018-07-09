package com.example.mathurinbloworlf.moviestreamapp.fragment.movie_feed;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.adapter.CustomMovieAdapter;
import com.example.mathurinbloworlf.moviestreamapp.adapter.MovieAdapter;
import com.example.mathurinbloworlf.moviestreamapp.constants.MovieDB;
import com.example.mathurinbloworlf.moviestreamapp.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TopRated extends Fragment {

    /*
    ArrayList<Movie> movies;
    MovieAdapter movieAdapter;
    ListView listView;
    */

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    CustomMovieAdapter customMovieAdapter;
    ArrayList<Movie> movieArrayList;

    SwipeRefreshLayout swipeRefreshLayout;
    //String url = "https://api.themoviedb.org/3/movie/top_rated?page=1&language=en-US&?api_key="+ MovieDB.key;
    //String url = "https://api.themoviedb.org/3/movie/top_rated?api_key="+ MovieDB.key;
    private int page = 1;

    public TopRated(){
        //requires an empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.top_rated, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        listView = view.findViewById(R.id.top_rated_listview);
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(getContext(), movies);
        listView.setAdapter(movieAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }
        */

        recyclerView = view.findViewById(R.id.top_rated_recycler);
        movieArrayList = new ArrayList<>();
        //loadNowPlaying(1);

        /*
        if(customMovieAdapter.getScreenOrientation() == 1){
            gridLayoutManager = new GridLayoutManager(getContext(), 1);
        }
        else if(customMovieAdapter.getScreenOrientation() == 2){
            gridLayoutManager = new GridLayoutManager(getContext(), 2);
        }
        */
        gridLayoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(gridLayoutManager);

        customMovieAdapter = new CustomMovieAdapter(getContext(), movieArrayList);
        recyclerView.setAdapter(customMovieAdapter);
        swipeRefreshLayout = view.findViewById(R.id.top_rated_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTopRated(1);
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        loadTopRated(1);

        /*
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
        */

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == movieArrayList.size()-1){
                    page++;
                    loadTopRated(page);
                }
            }
        });
        recyclerView.addOnItemTouchListener(new CustomMovieAdapter.RecyclerTouchListener(getContext(), recyclerView, new CustomMovieAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Intent intent = new Intent(getContext(), MovieSearch.class);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void loadTopRated(final int page) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/top_rated?api_key="+ MovieDB.key +"&page=" + page)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    //JSONArray array = new JSONArray(response.body().string());

                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);

                        Movie movie = new Movie(object.getString("id"),
                                object.getDouble("vote_average"),
                                object.getString("title"),
                                object.getString("poster_path"),
                                object.getString("backdrop_path"),
                                object.getString("overview"),
                                object.getString("release_date"));

                        movieArrayList.add(movie);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                swipeRefreshLayout.setRefreshing(false);
                customMovieAdapter.notifyDataSetChanged();
            }
        };
        task.execute(1);
    }
}
