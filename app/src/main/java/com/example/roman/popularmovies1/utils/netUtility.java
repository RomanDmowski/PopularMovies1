package com.example.roman.popularmovies1.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class netUtility {

    final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    final static String PARAM_KEY = "api_key";


    final static String PARAM_LANGUAGE = "language";
    final static String language = "en-US";

    final static String PARAM_SORT = "sort_by";

    final static String PARAM_PAGE = "page";
    final static String page_number = "1";

    public static URL buildURL (String sort_by, String user_key) {

        //https://api.themoviedb.org/3/discover/movie?api_key=<user_key>&language=en-US&sort_by=popularity.desc&page=1

        Uri uri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_LANGUAGE,language)
                .appendQueryParameter(PARAM_SORT,sort_by)
                .appendQueryParameter(PARAM_PAGE, page_number)
                .appendQueryParameter(PARAM_KEY,user_key)
                .build();
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }


    public static String getHttpResponse (URL url) throws IOException {


        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");


            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}