package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler db;

    ListView listView;
    String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        db = new SQLiteDatabaseHandler(this);

        setTitle("Favorite Movies");

        currentUsername = "test_user";


        List<FavoriteMovie> moviesList = db.getAllFavoriteMoviesForUser(currentUsername);
        String[] moviesArray = new String[moviesList.size()];
        for (int i=0; i<moviesList.size(); i++) {
            FavoriteMovie movie = moviesList.get(i);
            moviesArray[i] = movie.getMovieName();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.favorite_movies_list,moviesArray);
        listView = findViewById(R.id.movies_list);
        listView.setAdapter(adapter);
    }
}
