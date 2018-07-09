package com.example.mathurinbloworlf.moviestreamapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {
    /*
    {

    "vote_count": 1486,
    "id": 351286,
    "video": false,
    "vote_average": 6.6,
    "title": "Jurassic World: Fallen Kingdom",
    "popularity": 294.284,
    "poster_path": "/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg",
    "original_language": "en",
    "original_title": "Jurassic World: Fallen Kingdom",
    "genre_ids": [
        28,
        12,
        878
    ],
    "backdrop_path": "/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg",
    "adult": false,
    "overview": "Several years after the demise of Jurassic World, a volcanic eruption threatens the remaining dinosaurs on the island of Isla Nublar. Claire Dearing, the former park manager and founder of the Dinosaur Protection Group, recruits Owen Grady to help prevent the extinction of the dinosaurs once again.",
    "release_date": "2018-06-06"

    }
    */
    //int vote_count;
    String id;
    //boolean video;
    double vote_average;
    String title;
    //double popularity;
    String poster_path;
    //String original_language;
    String original_title;
    //
    String backdrop_path;
    //boolean adult;
    String overview;
    String release_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", poster_path);
    }

    public String getBackdrop_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdrop_path);
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Movie(JSONObject jsonObject) throws JSONException{
        this.id = jsonObject.getString("id");
        this.poster_path = jsonObject.getString("poster_path");
        this.backdrop_path = jsonObject.getString("backdrop_path");
        this.original_title = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.title = jsonObject.getString("title");
    }

    public Movie(String id, Double vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for(int i=0;i<array.length();i++){
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
