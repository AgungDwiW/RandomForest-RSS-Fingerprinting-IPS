package com.example.ipswalker;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Info> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
        }
    }


    public Adapter(List<Info> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row , parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Info movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.subtitle.setText(movie.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
