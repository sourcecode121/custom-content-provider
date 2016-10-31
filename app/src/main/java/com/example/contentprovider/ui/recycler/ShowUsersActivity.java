package com.example.contentprovider.ui.recycler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.contentprovider.R;
import com.example.contentprovider.data.UsersDB;
import com.example.contentprovider.model.User;
import com.example.contentprovider.ui.MainActivity;
import com.example.contentprovider.ui.UpdateUserActivity;

import java.util.List;

/**
 * Created by Anand on 28/10/2016.
 */

public class ShowUsersActivity extends AppCompatActivity implements OnItemClickListener {

    private UsersDB usersDB;
    private List<User> users;
    private RecyclerView recyclerView;
    private ShowUsersAdapter showUsersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int option;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_users);

        option = getIntent().getExtras().getInt("option");
        switch (option) {
            case 1:
                setTitle("All Users");
                break;
            case 2:
                setTitle("Update User");
                break;
            case 3:
                setTitle("Delete User");
                break;
        }

        usersDB = new UsersDB(this);
        users = usersDB.getUsers();

        recyclerView = (RecyclerView) findViewById(R.id.show_users_recycler_view);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        showUsersAdapter = new ShowUsersAdapter(users);
        recyclerView.setAdapter(showUsersAdapter);

        showUsersAdapter.setClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (option) {
            case 1:
                Toast.makeText(this, users.get(position).getFirstName(), Toast.LENGTH_SHORT).show();
                break;
            case 2:
                intent = new Intent(ShowUsersActivity.this, UpdateUserActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                break;
            case 3:
                deleteUser(position);
                break;
        }
    }

    private void deleteUser(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowUsersActivity.this);
        builder.setTitle("Are you sure?")
                .setMessage(String.format("Delete %s %s",
                            users.get(position).getFirstName(),
                            users.get(position).getLastName()))
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(usersDB.deleteUserDetails(users.get(position).getId())) {
                            Toast.makeText(ShowUsersActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ShowUsersActivity.this, MainActivity.class));
                        }
                        else {
                            Toast.makeText(ShowUsersActivity.this, "Unable to delete user", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create()
                .show();
    }
}
