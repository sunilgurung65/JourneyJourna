package com.example.journey_journal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email_txt,password_edit;
    Button login_btn;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        email_txt=findViewById(R.id.edit_email);
        password_edit=findViewById(R.id.edit_password);
        login_btn=findViewById(R.id.login);
        mauth=FirebaseAuth.getInstance();

        login_btn.setOnClickListener(view -> {
//            String login_email=email_txt.getText().toString();
//            String login_password=password_edit.getText().toString();
//
//            if (TextUtils.isEmpty(login_email)){
//                Toast.makeText(MainActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (TextUtils.isEmpty(login_password)){
//                Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            mauth.signInWithEmailAndPassword(login_email,login_password)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                          if(task.isSuccessful()){
                              startActivity(new Intent(getApplicationContext(),Home.class));
                              Toast.makeText(MainActivity.this, "Login Conp", Toast.LENGTH_SHORT).show();

//                          }else {
//                              Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                          }
//                        }
//                    });


        });


    }

    public void signup(View view) {

        Intent intent=new Intent(MainActivity.this,Signup.class);
        startActivity(intent);
    }

    public void forgot(View view) {
        Intent intent=new Intent(MainActivity.this,ForgotPassword.class);
        startActivity(intent);
    }


}