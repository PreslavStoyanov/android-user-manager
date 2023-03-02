package com.example.user.user_manager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    final Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final TextView username = findViewById(R.id.usernameBox);
        final TextView password = findViewById(R.id.passwordBox);
        final Button login = findViewById(R.id.loginButton);
        ImageView image = findViewById(R.id.imageView);
        image.setImageResource(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_main);

        database.addUser(new User("root", "root"));

        login.setOnClickListener(v -> login(username, password));
    }

    private void login(TextView username, TextView password)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new MainActivity());

        for (User user : database.getAllUsers())
        {
            if (username.getText().toString().equals(user.getUsername())
                    && password.getText().toString().equals(user.getPassword()))
            {
                alertDialogBuilder
                        .setMessage("Login Successful!")
                        .setPositiveButton("Ок", (arg0, arg1) ->
                                startActivity(new Intent(MainActivity.this, WorkActivity.class)));
            } else
            {
                alertDialogBuilder.setMessage("Unsuccessful !");
            }
        }

        alertDialogBuilder.create().show();
    }
}
