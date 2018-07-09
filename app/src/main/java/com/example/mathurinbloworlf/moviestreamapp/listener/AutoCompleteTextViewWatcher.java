package com.example.mathurinbloworlf.moviestreamapp.listener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.constants.MovieDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AutoCompleteTextViewWatcher implements TextWatcher {

    private Handler handler = new Handler(Looper.getMainLooper());

    private AutoCompleteTextView autoCompleteTextView;
    private Fragment fragment;
    private Context context;

    public AutoCompleteTextViewWatcher(Context context, AutoCompleteTextView autoCompleteTextView, Fragment fragment) {
        this.context = context;
        this.autoCompleteTextView = autoCompleteTextView;
        this.fragment = fragment;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        switch (autoCompleteTextView.getId()){
            case R.id.input_search:
                suggestion(charSequence.toString());
                break;
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void suggestion(final String input){
        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                ArrayList<String> arrayList = new ArrayList<>();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/search/movie?api_key="+ MovieDB.key+ "&query="+ input)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    //JSONArray array = new JSONArray(response.body().string());

                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);

                        arrayList.add(object.getString("title"));
                    }
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayList);
                    //autoCompleteTextView.setAdapter(arrayAdapter);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            autoCompleteTextView.setAdapter(arrayAdapter);
                        }
                    }, 500);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //swipeRefreshLayout.setRefreshing(false);
                //customMovieAdapter.notifyDataSetChanged();
            }
        };
        task.execute(1);
    }
}
