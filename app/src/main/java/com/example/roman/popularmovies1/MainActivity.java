package com.example.roman.popularmovies1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.roman.popularmovies1.utils.jsonUtility;
import com.example.roman.popularmovies1.utils.netUtility;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerImageAdapter.GridItemClickListener, AdapterView.OnItemSelectedListener {


    private RecyclerImageAdapter mAdapter;
    private String mJsonResults;
    private String mSortParameter;
    private int mSpinnerIndexSelected;
    private List<Movie> mMovies;
    private String mMovieDbKey;

    private static final String LIFECYCLE_CALLBACKS_SPINNER_SELECTED_INDEX= "selectedSpinner";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieDbKey = getString(R.string.movie_db_key);
        String movieUrlBase = getString(R.string.movie_url_base) + "/w185";



        mJsonResults="";
        mMovies=null;


        setContentView(R.layout.activity_main);

        Context mContext = this;
        RecyclerView mRecyclerView = findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerImageAdapter(mContext,this,movieUrlBase);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(LIFECYCLE_CALLBACKS_SPINNER_SELECTED_INDEX,mSpinnerIndexSelected);
        editor.apply();

    }

    private void LoadMovies(){
        URL moviesUrl = netUtility.buildURL(mSortParameter,mMovieDbKey);
        new MovieDbQueryTask().execute(moviesUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        MenuItem menuItem = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) menuItem.getActionView();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_sort_order_array, R.layout.spinner_item);

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSpinnerIndexSelected= preferences.getInt(LIFECYCLE_CALLBACKS_SPINNER_SELECTED_INDEX,0);
        spinner.setSelection(mSpinnerIndexSelected);

        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Movie selectedMovie = mMovies.get(clickedItemIndex);
        Class destinationActivity = DetailsActivity.class;
        Intent startDestinationActivityIntent = new Intent(this, destinationActivity);
        startDestinationActivityIntent.putExtra("Movie",selectedMovie);
        startActivity(startDestinationActivityIntent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        mSpinnerIndexSelected=position;

        if (getString(R.string.sort_order_popular_label).equals(parent.getItemAtPosition(position))){
            mSortParameter = getString(R.string.sort_order_popular_param);
        }
        else  {
            mSortParameter = getString(R.string.sort_order_rate_param);
        }

        LoadMovies();

//        String toastMessage = "Position:" + position + ", id=" + id + ", item=" + parent.getItemAtPosition(position);
//        Toast mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
//
//        mToast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @SuppressLint("StaticFieldLeak")
    class MovieDbQueryTask extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL queryUrl = urls[0];
            String queryResult = null;

            try {
                queryResult=netUtility.getHttpResponse(queryUrl);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return queryResult;
        }

        @Override
        protected void onPostExecute(String movieDbQueryResult) {
            //super.onPostExecute(movieDbQueryResult);

            if (movieDbQueryResult!=null) {
                mJsonResults=movieDbQueryResult;
                    mMovies= jsonUtility.parseMoviesDbJson(mJsonResults);
                    mAdapter.setMovieData(mMovies);
            }
        }
    }
}
