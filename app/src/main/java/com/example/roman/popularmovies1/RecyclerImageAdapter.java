package com.example.roman.popularmovies1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.ViewHolder>{

    private List<Movie> mMovieList;
    private static Context mContext;
    //private static final String URL_BASE = "http://image.tmdb.org/t/p/w185";
    private static String mUrlBase;

    private  GridItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;
        public ViewHolder(View view){
            super(view);
            mImageView = view.findViewById(R.id.movies_grid_item_image_view);
            view.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            mClickListener.onListItemClick(position);
        }
    }

    public RecyclerImageAdapter (Context context, GridItemClickListener listener,String urlBase ) {
        mContext = context;
        mClickListener=listener;
        mUrlBase = urlBase;

    }

    public  RecyclerImageAdapter (Context context, GridItemClickListener listener, List<Movie> movieList, String urlBase) {
        mMovieList = movieList;
        mUrlBase = urlBase;
        mContext = context;
        mClickListener=listener;
    }



    public interface GridItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public RecyclerImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_grid_item,null);

        ViewHolder vh = new ViewHolder(itemLayoutView);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerImageAdapter.ViewHolder holder, int position) {
        //ImageView iv = null;

        String posterPath = mMovieList.get(position).getPosterPath();
        String fullPathPoster = mUrlBase + posterPath;
        Picasso.with(mContext).load(fullPathPoster).placeholder(R.drawable.placeholder185px).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        if (mMovieList ==null) {
            return 0;
        }
        return mMovieList.size();
    }


    public void setMovieData(List<Movie> movieData) {
        mMovieList = movieData;
        notifyDataSetChanged();
    }
}
