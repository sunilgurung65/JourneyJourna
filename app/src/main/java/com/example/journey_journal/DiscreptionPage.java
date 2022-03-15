package com.example.journey_journal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class DiscreptionPage extends AppCompatActivity {
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discreption_page);

        submit=findViewById(R.id.btn_submit);

        submit.setAlpha(0f);
        submit.animate().alpha(1f).setDuration(1500);
    }
}