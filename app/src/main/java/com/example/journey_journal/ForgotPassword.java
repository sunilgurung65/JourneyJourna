package com.example.journey_journal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText Email_txt;
    private Button send_email;
    private FirebaseAuth auth;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();


        Email_txt=(EditText)findViewById(R.id.email);
        send_email=(Button)findViewById(R.id.forgot);

        auth=FirebaseAuth.getInstance();

        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
    }

    private void validatedata() {
        String email=Email_txt.getText().toString();
        if (email.isEmpty()){
            Email_txt.setError("Required Email ID");

        }else {
            Forgotpassword();
        }
    }


    private void Forgotpassword() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,
                            "Check Your Email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPassword.this,
                            MainActivity.class));
                    finish();

                }else {
                    Toast.makeText(ForgotPassword.this,
                            "Error:"+task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void Signup(View view) {
        startActivity(new Intent(getApplicationContext(),Signup.class));
    }
}