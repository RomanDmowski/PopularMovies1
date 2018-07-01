package com.example.roman.popularmovies1.utils;

import com.example.roman.popularmovies1.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class jsonUtility {


    public static List<Movie> parseMoviesDbJson(String json) throws JSONException {



//        this.title = title;                 //"title": "Jurassic World: Fallen Kingdom",
//        this.releaseDate = releaseDate;     //"release_date": "2018-06-06"
//        this.voteAverage = voteAverage;     //"vote_average": 6.8,
//        this.plotSynopsis = plotSynopsis;   //"overview"
//        this.posterPath = posterPath;       //"poster_path": "/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
//        this.backdropPath = backdropPath;   //"backdrop_path": "/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg",

        String title;
        String releaseDate;
        double voteAverage;
        String plotSynopsis;
        String posterPath;
        String backdropPath;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultsArray = jsonObject.getJSONArray("results");
            Movie thisMovie = new Movie();
            List<Movie> movieListResult = new ArrayList<>();

            for (int i=0; i< resultsArray.length();i++){

                JSONObject jsonMovie= resultsArray.getJSONObject(i);
                title = jsonMovie.getString("title");
                releaseDate = jsonMovie.getString("release_date");
                voteAverage = jsonMovie.getDouble("vote_average");
                plotSynopsis = jsonMovie.getString("overview");
                posterPath = jsonMovie.getString("poster_path");
                backdropPath = jsonMovie.getString("backdrop_path");
                Movie m = new Movie(title,releaseDate,voteAverage,plotSynopsis,posterPath, backdropPath);
                movieListResult.add(m);
            }

            return movieListResult;

        }
        catch (JSONException j) {
            return null;
        }
    }
}
