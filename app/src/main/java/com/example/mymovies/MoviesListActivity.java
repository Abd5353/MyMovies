package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MoviesListActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler db;

    TextView movieInfo;
    SearchView searchView;
    Button showPosterButton;
    Button addToFavoritesButton;
    String currentmoviename;
    String currentmovieyear;
    String currentimdbID;
    String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        currentUsername = "test_user";

        db = new SQLiteDatabaseHandler(this);

        setTitle("Welcome!");

        searchView = findViewById(R.id.searchView);
        movieInfo = findViewById(R.id.movieInfo);
        showPosterButton = findViewById(R.id.showPosterButton);
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getMovie(newText);
                return false;
            }
        });

        movieInfo.setText("Movie Title: \n Movie Year: \n Movie Director: \n Movie Rating: \n");
    }

    public void getMovie(String movie){
        movie = movie.replaceAll(" ","+");
        String url = "http://www.omdbapi.com/?t="+movie+"&apikey=1154cb05";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String movieInfoText = "Movie Title: " + response.getString("Title");
                    movieInfoText += "\n Movie Year: " + response.getString("Year");
                    movieInfoText += "\n Movie Director: " + response.getString("Director");
                    movieInfoText += "\n Movie Genre: " + response.getString("Genre");
                    currentmoviename = response.getString("Title");
                    currentmovieyear = response.getString("Year");
                    currentimdbID = response.getString("imdbID");
                    movieInfo.setText(movieInfoText);
                    showPosterButton.setEnabled(true);
                    addToFavoritesButton.setEnabled(true);
                }
                catch (JSONException e) {
                    movieInfo.setText("Error! Movie not found!");
                    showPosterButton.setEnabled(false);
                    addToFavoritesButton.setEnabled(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void showPoster(View v) {
        String url = "http://img.omdbapi.com/?i="+currentimdbID+"&apikey=1154cb05";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "image/*");
        startActivity(intent);
    }

    public void addToFavorites(View view) {
        FavoriteMovie movie = new FavoriteMovie(0, currentUsername, currentmoviename, currentmovieyear, currentimdbID);
        db.addFavoriteMovie(movie);
    }

    public void removeFromFavorites() {

    }

    public void showFavorites(View view) {
        Intent favoriteMoviesListIntent = new Intent(view.getContext(),FavoritesActivity.class);
        startActivityForResult(favoriteMoviesListIntent,0);
    }
}
