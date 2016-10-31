package com.example.contentprovider.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contentprovider.R;
import com.example.contentprovider.data.UsersDB;

/**
 * Created by Anand on 27/10/2016.
 */
public class AddUserActivity extends AppCompatActivity {

    private UsersDB usersDB;
    private EditText firstName;
    private EditText lastName;
    private EditText age;
    private boolean result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        usersDB = new UsersDB(this);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        age = (EditText) findViewById(R.id.age);

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = usersDB.insertUserDetails(
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        Integer.parseInt(age.getText().toString())
                );
                if(result) {
                    firstName.setText("");
                    lastName.setText("");
                    age.setText("");
                    firstName.requestFocus();
                    Toast.makeText(AddUserActivity.this, "Added user to the database", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddUserActivity.this, "Unable to add the user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
