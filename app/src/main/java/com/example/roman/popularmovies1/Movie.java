package com.example.roman.popularmovies1;


import android.os.Parcel;
import android.os.Parcelable;

//Movie model
public class Movie implements Parcelable {
    //contains title, release date, movie poster, vote average, and plot synopsis
    //poster_path
    private String title;
    private String releaseDate;
    private double voteAverage;
    private String plotSynopsis;
    private String posterPath;



    private String backdropPath;


    @SuppressWarnings("unused")
    public Movie () {

    }

    private Movie(Parcel in){
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        plotSynopsis = in.readString();
        posterPath = in.readString();
        backdropPath=in.readString();

    }

    @SuppressWarnings("SpellCheckingInspection")
    public Movie(String title, String releaseDate, double voteAverage, String plotSynopsis, String posterPath , String backdropPath) {
        this.title = title;                 //"title": "Jurassic World: Fallen Kingdom",
        this.releaseDate = releaseDate;     //"release_date": "2018-06-06"
        this.voteAverage = voteAverage;     //"vote_average": 6.8,
        this.plotSynopsis = plotSynopsis;   //"overview"
        this.posterPath = posterPath;       //"poster_path": "/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
        this.backdropPath = backdropPath;   //"backdrop_path": "/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg",
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeDouble(voteAverage);
        parcel.writeString(plotSynopsis);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };




    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public String getPosterPath() {
        return posterPath;
    }

}

