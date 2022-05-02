package com.androiddatabase.loginregister.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androiddatabase.loginregister.adapters.UsersRecyclerAdapter;
import com.androiddatabase.loginregister.model.User;
import com.androiddatabase.loginregister.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import androiddatabase.loginregister.R;


public class UsersListActivity extends AppCompatActivity {
    private Button logout;
    private Button ta2,ta3;
    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private AppPreferences appPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        getSupportActionBar().setTitle("");
        initViews();
        initListeners();
        initObjects();


    }
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        logout = (Button) findViewById(R.id.out);
        ta2 = (Button) findViewById(R.id.t2);
        ta3 = (Button) findViewById(R.id.t3);
    }

    private void initListeners() {

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPreferences.clearPrefs();

                // navigating to the profile making activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });
        ta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPreferences.clearPrefs();

                // navigating to the profile making activity
                Intent intent = new Intent(getApplicationContext(),task2activity.class);
                startActivity(intent);

            }
        });
        ta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPreferences.clearPrefs();

                // navigating to the profile making activity
                Intent intent = new Intent(getApplicationContext(),task3activity.class);
                startActivity(intent);

            }
        });

    }

    /**
     * This method is to initialize views
     */


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
