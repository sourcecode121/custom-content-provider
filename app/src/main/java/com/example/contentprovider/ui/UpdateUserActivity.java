package com.example.contentprovider.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contentprovider.R;
import com.example.contentprovider.data.UsersDB;

/**
 * Created by Anand on 29/10/2016.
 */

public class UpdateUserActivity extends AppCompatActivity {

    private UsersDB usersDB;
    private EditText firstName;
    private EditText lastName;
    private EditText age;
    private boolean result;
    private int position;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);

        position = getIntent().getExtras().getInt("position");

        usersDB = new UsersDB(this);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        age = (EditText) findViewById(R.id.age);

        firstName.setText(usersDB.getUsers().get(position).getFirstName());
        lastName.setText(usersDB.getUsers().get(position).getLastName());
        age.setText(String.valueOf(usersDB.getUsers().get(position).getAge()));
        firstName.requestFocus();

        id = usersDB.getUsers().get(position).getId();

        findViewById(R.id.update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = usersDB.updateUserDetails(
                        id,
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        Integer.parseInt(age.getText().toString())
                );
                if(result) {
                    Toast.makeText(UpdateUserActivity.this, "User details updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateUserActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(UpdateUserActivity.this, "Unable to update user details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
