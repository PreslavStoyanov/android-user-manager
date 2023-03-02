package com.example.user.user_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WorkActivity extends AppCompatActivity
{

    final Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        final TextView userList = new TextView(this);
        Button logout = findViewById(R.id.logout_button);
        Button showAllUsers = findViewById(R.id.all_users_button);
        Button addUser = findViewById(R.id.add_user_button);
        Button deleteUser = findViewById(R.id.delete_user_button);
        Button search = findViewById(R.id.search_button);

        addUser.setOnClickListener(v -> startActivity(new Intent(WorkActivity.this, AddUserActivity.class)));

        showAllUsers.setOnClickListener(v ->
        {
            for (User user : database.getAllUsers()) {
                userList.setText(String.format(getString(R.string.userView),
                        user.getId(), user.getUsername(),user.getPassword()));
            }
            Toast.makeText(WorkActivity.this,"All users is loaded!", Toast.LENGTH_LONG).show();
        });


        logout.setOnClickListener(v -> startActivity(new Intent(WorkActivity.this, MainActivity.class)));
    }
}