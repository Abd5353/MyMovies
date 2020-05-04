package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private UsersDatabaseHandler db;

    EditText usernameTF;
    EditText passwordTF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new UsersDatabaseHandler(this);

        usernameTF = findViewById(R.id.usernameTF);
        passwordTF = findViewById(R.id.passwordTF);
    }

    public void signIn(View view) {
        User user = new User(0,usernameTF.getText().toString(), passwordTF.getText().toString());
        if (db.doesUserExist(user)) {
            Toast.makeText(getApplicationContext(),
                    "Redirecting...", Toast.LENGTH_LONG).show();
            Intent moviesListIntent = new Intent(view.getContext(),MoviesListActivity.class);
            startActivityForResult(moviesListIntent,0);
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_LONG).show();
        }
    }

    public void signUp(View view) {
        User user = new User(0,usernameTF.getText().toString(), passwordTF.getText().toString());
        db.addUser(user);
        Toast.makeText(getApplicationContext(), "New User Created", Toast.LENGTH_LONG).show();
        Intent moviesListIntent = new Intent(view.getContext(),MoviesListActivity.class);
        startActivityForResult(moviesListIntent,0);
    }
}
