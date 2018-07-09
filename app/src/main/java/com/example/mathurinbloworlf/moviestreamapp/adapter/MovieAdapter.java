package com.example.mathurinbloworlf.moviestreamapp.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.constants.MovieDB;
import com.example.mathurinbloworlf.moviestreamapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    Context context;

    private static class ViewHolder{
        ImageView movie_poster;
        TextView movie_title;
        TextView movie_overview;
        //ImageButton play_preview;
        //VideoView movie_preview;

    }

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_dropdown_item_1line, movies);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get data item for this position
        final Movie movie = getItem(position);

        //Check if the existing view is being reused
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_model, parent, false);
            //viewHolder.movie_preview = convertView.findViewById(R.id.movie_video);
            //viewHolder.play_preview = convertView.findViewById(R.id.play_movie_preview);
            viewHolder.movie_poster = convertView.findViewById(R.id.movie_poster);
            viewHolder.movie_title = convertView.findViewById(R.id.movie_title);
            viewHolder.movie_overview = convertView.findViewById(R.id.movie_overview);

            convertView.setTag(viewHolder);
        }
        else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //if(movie.getVote_average() > 5.0){
        //    viewHolder.play_preview.setVisibility(View.VISIBLE);
        //}
        //viewHolder.play_preview.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        viewHolder.movie_poster.setVisibility(View.GONE);
        //        viewHolder.play_preview.setVisibility(View.GONE);
        //        viewHolder.movie_preview.setVisibility(View.VISIBLE);
        //        viewHolder.movie_preview.setVideoURI(Uri.parse("https://api.themoviedb.org/3/movie/"+movie.getId()+"video?api_key="+ MovieDB.key));
        //    }
        //});
        //viewHolder.movie_preview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        //    @Override
        //    public void onPrepared(MediaPlayer mediaPlayer) {
        //        viewHolder.movie_preview.start();
        //    }
        //});
        //viewHolder.movie_preview.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        if(viewHolder.movie_preview.isPlaying()){
        //            viewHolder.movie_preview.pause();
        //        }
        //        else {
        //            viewHolder.movie_preview.start();
        //        }
        //    }
        //});
        //viewHolder.movie_preview.setOnLongClickListener(new View.OnLongClickListener() {
        //    @Override
        //    public boolean onLongClick(View view) {
        //        viewHolder.movie_preview.stopPlayback();
        //        viewHolder.movie_preview.setVisibility(View.GONE);
        //        viewHolder.movie_poster.setVisibility(View.GONE);
        //        if(movie.getVote_average() < 5.0){
        //            viewHolder.play_preview.setVisibility(View.GONE);
        //        }
        //        else{
        //            viewHolder.play_preview.setVisibility(View.VISIBLE);
        //        }
        //        return false;
        //    }
        //});

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.movie_title.setText(movie.getOriginal_title());
        viewHolder.movie_overview.setText(movie.getOverview());

        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(context)
                    .load(movie.getPoster_path())
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .into(viewHolder.movie_poster);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Glide.with(context)
                    .load(movie.getBackdrop_path())
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .into(viewHolder.movie_poster);
        }

        // Return the completed view to render on screen
        return convertView;

    }
}
