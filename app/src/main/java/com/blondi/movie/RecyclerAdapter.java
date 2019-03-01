package com.blondi.movie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import  com.blondi.movie.R;
import com.bumptech.glide.Glide;


/**
 * Created by Enio on 1/20/2019.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mcontext;
    private List<Movie> mMovies;

    public RecyclerAdapter(Context mycntext, List<Movie> mMovies) {
        this.mcontext = mycntext;
        this.mMovies = mMovies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view=inflater.inflate(R.layout.cell,parent,false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvMovieType.setText(mMovies.get(position).getType());
        holder.tvMovieYear.setText(mMovies.get(position).getYear());
        holder.tvMovieTitle.setText(mMovies.get(position).getTitle());



        Glide.with(mcontext)
                .load(mMovies.get(position).getPoster())
                .into(holder.ivMoviePoster);



        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mcontext, mMovies.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(mcontext,DetailActivity.class);
                intent.putExtra("image_URL", mMovies.get(position).getPoster());
                intent.putExtra("key", mMovies.get(position).getImdbID());
                intent.putExtra("title",mMovies.get(position).getTitle());
                mcontext.startActivity(intent);

            }
        });





    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tvMovieTitle;
        TextView tvMovieYear;
        TextView tvMovieType;
        ImageView ivMoviePoster;
        RelativeLayout parentLayout;



        public MyViewHolder(View itemView) {
            super(itemView);

            tvMovieTitle= itemView.findViewById(R.id.tvMovieTitle);
            tvMovieYear = itemView.findViewById(R.id.tvMovieYear);
            tvMovieType = itemView.findViewById(R.id.tvmovieType);
            ivMoviePoster= itemView.findViewById(R.id.ivMoviePoster);

            parentLayout=itemView.findViewById(R.id.parent_layout);



        }
    }

}
