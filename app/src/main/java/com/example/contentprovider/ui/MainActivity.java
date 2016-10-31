package com.example.contentprovider.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.contentprovider.R;
import com.example.contentprovider.ui.recycler.ShowUsersActivity;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add_user_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddUserActivity.class));
            }
        });

        findViewById(R.id.show_users_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, ShowUsersActivity.class);
                intent.putExtra("option", 1);
                startActivity(intent);
            }
        });

        findViewById(R.id.update_details_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, ShowUsersActivity.class);
                intent.putExtra("option", 2);
                startActivity(intent);
            }
        });

        findViewById(R.id.delete_user_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, ShowUsersActivity.class);
                intent.putExtra("option", 3);
                startActivity(intent);
            }
        });
    }
}
