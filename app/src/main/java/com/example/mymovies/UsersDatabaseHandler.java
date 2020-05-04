package com.example.mymovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class UsersDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UsersDB";
    private static final String TABLE_NAME = "Users";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String[] COLUMNS = { KEY_ID, KEY_USER_NAME, KEY_PASSWORD};

    public UsersDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Users ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "username TEXT, "
                + "password TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUserName());
        values.put(KEY_PASSWORD, user.getPassword());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public Boolean doesUserExist(User user) {
        List<User> users = new LinkedList<User>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                User testUser = new User(0, cursor.getString(1),cursor.getString(2));
                if (user.getUserName().equals(cursor.getString(1)) && user.getPassword().equals(cursor.getString(2)))
                {
                    return true;
                }
            } while (cursor.moveToNext());
        }

        return false;
    }
}
