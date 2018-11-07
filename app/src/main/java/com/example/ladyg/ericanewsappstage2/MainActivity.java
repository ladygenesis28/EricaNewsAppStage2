package com.example.ladyg.ericanewsappstage2;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String JSON_URL_NEWS = "http://content.guardianapis.com/search?format=json&show-tags=contributor&api-key=b3ee86e0-e862-44ff-81e5-421bd5983e37";

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final int NEWS_LOADER_ID = 1;


    /**
     * Adapter for the list of com.example.ladyg.ericanewsappstage2.News
     */
    private RecyclerViewAdapter mAdapter;

    private RecyclerView recyclerView;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);


        // Create a new adapter that takes an empty list of news as input
        mAdapter = new RecyclerViewAdapter(this, new ArrayList<News>());

        // Obtain a reference to the SharedPreferences file for this app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // And register to be notified of preference changes
        // So we know when the user has adjusted the query settings
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager getLoaderManager = getLoaderManager();

        //Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            //display textview tell user there's not internet connection
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        @Override
        public void onSharedPreferenceChanged(SharedPreferences prefs, String s){
                if (key.equals(getString(R.string.settings_min_magnitude_key)) ||
                        key.equals(getString(R.string.settings_order_by_key))) {
                    // Clear the ListView as a new query will be kicked off
                    mAdapter.clear();

                    // Hide the empty state text view as the loading indicator will be displayed
                    mEmptyStateTextView.setVisibility(View.GONE);

                    // Show the loading indicator while new data is being fetched
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.VISIBLE);

                    // Restart the loader to requery the USGS as the query settings have been updated
                    getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
                }

                @Override
                public Loader<List<News>> onCreateLoader ( int i, Bundle bundle){
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                    String minNews = sharedPrefs.getString(
                            getString(R.string.settings_min_news_key),
                            getString(R.string.settings_min_news_default));

                    String orderBy = sharedPrefs.getString(
                            getString(R.string.settings_order_by_key),
                            getString(R.string.settings_order_by_default)
                    );

                    Uri baseUri = Uri.parse(JSON_URL_NEWS);
                    Uri.Builder uriBuilder = baseUri.buildUpon();

                    uriBuilder.appendQueryParameter("format", "geojson");
                    uriBuilder.appendQueryParameter("limit", "10");
                    uriBuilder.appendQueryParameter("minnews", minNews);
                    uriBuilder.appendQueryParameter("orderby", "dates");

                    return new NewsLoader(this, uriBuilder.toString());
                }

                @Override
                public void onLoadFinished (Loader < List < News >> loader, List < News > data){

                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);

                    // Set empty state text to display "No com.example.ladyg.ericanewsappstage2.News found."
                    mEmptyStateTextView.setText(R.string.no_news_found);

                    mAdapter.clear();

                    mAdapter = new RecyclerViewAdapter(MainActivity.this, data);
                    if (data != null && !data.isEmpty()) {
                        recyclerView.setAdapter(mAdapter);

                    }
                }

                @Override
                public void onLoaderReset (Loader < List < News >> loader) {
                    // Loader reset, so we can clear out our existing data.
                    mAdapter.clear();
                }

                @Override
                public boolean onCreateOptionsMenu (Menu menu){
                    getMenuInflater().inflate(R.menu.main, menu);
                    return true;
                }

                @Override
                public boolean onOptionsItemSelected (MenuItem item){
                    int id = item.getItemId();
                    if (id == R.id.action_settings) {
                        Intent settingsIntent = new Intent(this, SettingsActivity.class);
                        startActivity(settingsIntent);
                        return true;
                    }
                    return super.onOptionsItemSelected(item);
                }
            }
        }
    }
}