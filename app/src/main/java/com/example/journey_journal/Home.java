package com.example.journey_journal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

   
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView=findViewById(R.id.navigation);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.home_menu:
                        Toast.makeText(Home.this, "Clicked Home Page ", Toast.LENGTH_SHORT).show();
                        Intent homeintent=new Intent(getApplicationContext(),Home.class);
                        startActivity(homeintent);
                        break;

                    case R.id.about_menu:
                        Toast.makeText(Home.this, "Clicked About Page ", Toast.LENGTH_SHORT).show();
                        Intent aboutintent=new Intent(getApplicationContext(),About.class);
                        startActivity(aboutintent);
                        break;

                    case R.id.contact_menu:
                        Toast.makeText(Home.this, "Clicked contact  Page ", Toast.LENGTH_SHORT).show();
                        Intent conintent=new Intent(getApplicationContext(),Contact.class);
                        startActivity(conintent);
                        break;

                    case R.id.share_menu:
                        Toast.makeText(Home.this, "Clicked contact  Page ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/rahul.loni.509")));

                        break;
                }


                return false;
            }
        });


        final DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        findViewById(R.id.imagemenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }
}