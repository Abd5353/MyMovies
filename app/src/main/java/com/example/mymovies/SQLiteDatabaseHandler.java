package com.example.mymovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FavoriteMoviesDB";
    private static final String TABLE_NAME = "FavoriteMovies";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_MOVIE_NAME = "moviename";
    private static final String KEY_MOVIE_YEAR = "movieyear";
    private static final String KEY_IMDB_ID = "imdbid";
    private static final String[] COLUMNS = { KEY_ID, KEY_USER_NAME, KEY_MOVIE_NAME,
            KEY_MOVIE_YEAR, KEY_IMDB_ID };

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE FavoriteMovies ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "username TEXT, "
                + "moviename TEXT, " + "movieyear TEXT, " + "imdbid TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void addFavoriteMovie(FavoriteMovie favorteMovie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, favorteMovie.getUserName());
        values.put(KEY_MOVIE_NAME, favorteMovie.getMovieName());
        values.put(KEY_MOVIE_YEAR, favorteMovie.getMovieYear());
        values.put(KEY_IMDB_ID, favorteMovie.getImdbID());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public List<FavoriteMovie> getAllFavoriteMoviesForUser(String userName)
    {
        List<FavoriteMovie> movies = new LinkedList<FavoriteMovie>();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE username='" + userName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        FavoriteMovie movie = null;

        if (cursor.moveToFirst()) {
            do {
                movie = new FavoriteMovie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setUserName(cursor.getString(1));
                movie.setMovieName(cursor.getString(2));
                movie.setMovieYear(cursor.getString(3));
                movie.setImdbID(cursor.getString(4));
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        return movies;
    }
}
