package com.example.journey_journal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journey_journal.adapter.ItemsAdapter;
import com.example.journey_journal.adapter.RVItemClickListener;
import com.example.journey_journal.db.DbHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    Button btnAdd;
    ArrayList<ModelClass> journeys;
    // Database Layer
    DbHelper dbHelper;
    //RecyclerView
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;
    RVItemClickListener delImgViewClickListener;
    RVItemClickListener itemViewClickListener;

    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        journeys = new ArrayList<>();
        dbHelper = new DbHelper(this);
        btnAdd = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recycler);

        setupListeners();
        setupRecyclerView();
        initData();

        btnAdd.setOnClickListener(this::btnAddOnClick);

        //Bottom Navigation Start

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int items = item.getItemId();
                switch (items) {
                    case R.id.bottom_home:
                        Toast.makeText(Home.this, "Clickd Home in Bottom", Toast.LENGTH_SHORT).show();
                        Intent intent_home = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent_home);
                        break;
                    case R.id.bottom_about:
                        Toast.makeText(Home.this, "Clickd About in Bottom", Toast.LENGTH_SHORT).show();
                        Intent intent_about = new Intent(getApplicationContext(), About.class);
                        startActivity(intent_about);
                        break;
                    case R.id.bottom_contact:
                        Toast.makeText(Home.this, "Clickd Contact in Bottom", Toast.LENGTH_SHORT).show();
                        Intent intent_contact = new Intent(getApplicationContext(), Contact.class);
                        startActivity(intent_contact);
                        break;

                    case R.id.bottom_share:
                        Intent shareintent = new Intent(Intent.ACTION_SEND);
                        shareintent.setType("text/plain");
                        shareintent.putExtra(Intent.EXTRA_SUBJECT, "Your Application Link ");
                        shareintent.putExtra(Intent.EXTRA_TEXT, "Check ypur cool Application ");
                        startActivity(Intent.createChooser(shareintent, "Share via"));
                        break;

                }


                return false;
            }
        });

        //Bottom Navigation End


        //Top Navigation start

        navigationView = findViewById(R.id.navigation);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home_menu:
                        Toast.makeText(Home.this, "Clicked Home Page ", Toast.LENGTH_SHORT).show();
                        Intent homeintent = new Intent(getApplicationContext(), Home.class);
                        startActivity(homeintent);
                        break;

                    case R.id.about_menu:
                        Toast.makeText(Home.this, "Clicked About Page ", Toast.LENGTH_SHORT).show();
                        Intent aboutintent = new Intent(getApplicationContext(), About.class);
                        startActivity(aboutintent);
                        break;

                    case R.id.contact_menu:
                        Toast.makeText(Home.this, "Clicked contact  Page ", Toast.LENGTH_SHORT).show();
                        Intent conintent = new Intent(getApplicationContext(), Contact.class);
                        startActivity(conintent);
                        break;

                    case R.id.share_menu:
                        Intent shareintent = new Intent(Intent.ACTION_SEND);
                        shareintent.setType("text/plain");
                        shareintent.putExtra(Intent.EXTRA_TEXT, "https://www.facebook.com/ ");
                        shareintent.putExtra(Intent.EXTRA_SUBJECT, "Your Application link hare ");
                        startActivity(Intent.createChooser(shareintent, "share Via"));


                        break;
                }


                return false;
            }
        });

        //Top Navigation End

        //DrawerLayout  Menu
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        findViewById(R.id.imagemenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void btnAddOnClick(View view) {
        startActivity(DescriptionPage.getIntent(this, null));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initData() {
        String sqlQuery = "SELECT * FROM "+ DbHelper.TABLE_NAME;
        Cursor cursor = dbHelper.getAll(sqlQuery);
        journeys.clear();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ModelClass journey = new ModelClass(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                );
                Log.d("HOME", journey.getId()+"");
                journeys.add(journey);
            }
            itemsAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupListeners() {
        itemViewClickListener = (view, position) -> startActivity(
                DescriptionPage.getIntent(getApplicationContext(), journeys.get(position))
        );
        delImgViewClickListener = (view, position) -> {
            dbHelper.delete(journeys.get(position).getId());
            itemsAdapter.notifyDataSetChanged();
        };
    }

    //    private void initData() {
    //        userlist=new ArrayList<>();
    //        userlist.add(new ModelCLass(R.drawable.workshop,R.drawable.ic_baseline_delete_24,R.drawable.ic_baseline_edit_24
    //        ,"Rahul Kumar","Hello Rahul How are you ?","Chitawn Nepal-101010"));
    //        userlist.add(new ModelCLass(R.drawable.image4,R.drawable.ic_baseline_delete_24,R.drawable.ic_baseline_edit_24
    //                ,"Rohan Kumar","Hello Rohan How are you ?","Chitawn Nepal-101010"));
    //        userlist.add(new ModelCLass(R.drawable.image3,R.drawable.ic_baseline_delete_24,R.drawable.ic_baseline_edit_24
    //                ,"Rishu Kumar","Hello Rishu How are you ?","Chitawn Nepal-101010"));
    //        userlist.add(new ModelCLass(R.drawable.firbase2,R.drawable.ic_baseline_delete_24,R.drawable.ic_baseline_edit_24
    //                ,"Rahul Kumar","Hello Rahul How are you ?","Chitawn Nepal-101010"));
    //        userlist.add(new ModelCLass(R.drawable.image4,R.drawable.ic_baseline_delete_24,R.drawable.ic_baseline_edit_24
    //                ,"Rohan Kumar","Hello Rohan How are you ?","Chitawn Nepal-101010"));
    //        userlist.add(new ModelCLass(R.drawable.image3,R.drawable.ic_baseline_delete_24,R.drawable.ic_baseline_edit_24
    //                ,"Rishu Kumar","Hello Rishu How are you ?","Chitawn Nepal-101010"));
    //    }

    private void setupRecyclerView() {
        itemsAdapter = new ItemsAdapter(journeys, itemViewClickListener, delImgViewClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsAdapter);
    }


}