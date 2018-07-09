package com.example.mathurinbloworlf.moviestreamapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.constants.MovieDB;
import com.example.mathurinbloworlf.moviestreamapp.model.Movie;

import java.util.ArrayList;

public class CustomMovieAdapter extends RecyclerView.Adapter<CustomMovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movieArrayList;

    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CustomMovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        //holder.videoView.setVideoPath(media_list.get(position).getLink());

        holder.title.setText(movieArrayList.get(position).getTitle());
        holder.overview.setText(movieArrayList.get(position).getOverview());
        holder.release_date.setText("Release Date : "+movieArrayList.get(position).getRelease_date());

        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Glide.with(context)
                    .load(movieArrayList.get(position).getPoster_path())
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .into(holder.imageView);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Glide.with(context)
                    .load(movieArrayList.get(position).getBackdrop_path())
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .into(holder.imageView);
        }

        //Glide.with(context)
        //        .load(movieArrayList.get(position).getPoster_path())
        //        .thumbnail(Glide.with(context).load(R.drawable.loading))
        //        .into(holder.imageView);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        VideoView videoView;
        TextView overview, release_date, title;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            videoView = itemView.findViewById(R.id.video);
            overview = itemView.findViewById(R.id.overview);
            release_date = itemView.findViewById(R.id.release_date);
            title = itemView.findViewById(R.id.title);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private CustomMovieAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CustomMovieAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
