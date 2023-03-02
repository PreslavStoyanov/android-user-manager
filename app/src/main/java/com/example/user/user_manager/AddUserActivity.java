package com.example.user.user_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AddUserActivity extends AppCompatActivity
{

    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        final TextView username = findViewById(R.id.usernameBox);
        final TextView password = findViewById(R.id.passwordBox);
        Button back = findViewById(R.id.back_button);
        Button addUser = findViewById(R.id.add_user_button);
        Button clear = findViewById(R.id.clear_button);

        back.setOnClickListener(v -> startActivity(new Intent(AddUserActivity.this, WorkActivity.class)));

        addUser.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            database.addUser(new User(username.getText().toString(),password.getText().toString()));
            alertDialog.setMessage("User added!").create().show();
        });

        clear.setOnClickListener(v -> {
            username.setText("");
            password.setText("");
        });
    }
}