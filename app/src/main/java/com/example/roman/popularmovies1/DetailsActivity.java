package com.example.roman.popularmovies1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mImagePoster;
    private TextView mMovieTitleAndReleaseDate;
    private TextView mMovieVoteAverage;
    private TextView mMoviePlot;
    private String mUrlBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mImagePoster =  findViewById(R.id.iv_detail_movie_poster);
        mMovieTitleAndReleaseDate = findViewById(R.id.tv_detail_movie_title_and_release_date);
        mMovieVoteAverage = findViewById(R.id.tv_movie_vote_average);
        mMoviePlot = findViewById(R.id.tv_movie_plot);
        mUrlBase = getString(R.string.movie_url_base) + "/w500";

        Intent intent = getIntent();

        if (intent.hasExtra("Movie")){
            Movie movie = intent.getParcelableExtra("Movie");
            displayMovie(movie);
        }


    }

    private void displayMovie(Movie movie) {
        String title = movie.getTitle();
        String releaseDate = movie.getReleaseDate();
        String voteAverage = Double.toString( movie.getVoteAverage());
        String plot = movie.getPlotSynopsis();
        String posterPath = movie.getPosterPath();
        String fullPathPoster = mUrlBase + posterPath;
        Picasso.with(this).load(fullPathPoster).placeholder(R.drawable.placeholder185px).into(mImagePoster);
        String text1 = title + " (" + releaseDate + ")";
        mMovieTitleAndReleaseDate.setText(text1);
        text1 = voteAverage + "/10";
        mMovieVoteAverage.setText(text1);
        mMoviePlot.setText(plot);
    }
}