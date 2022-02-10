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

public class Signup extends AppCompatActivity {

    EditText email_edit,password_edit,confirmPassword_edit;
    Button singnup_btn;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();


        email_edit=findViewById(R.id.email);
        password_edit=findViewById(R.id.password);
        confirmPassword_edit=findViewById(R.id.cponfirmPassworrd);

        singnup_btn=findViewById(R.id.signup);

        mauth=FirebaseAuth.getInstance();

        singnup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=email_edit.getText().toString();
                String password=password_edit.getText().toString();
                String confirmpassword=confirmPassword_edit.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Signup.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Signup.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(confirmpassword)){
                    Toast.makeText(Signup.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (password.length()<6){
                    Toast.makeText(Signup.this, "Password to short", Toast.LENGTH_SHORT).show();

                }
                if(password.equals(confirmpassword)){
                    mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                Toast.makeText(Signup.this, "Login Com", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Signup.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }

    public void login_txt(View view) {

        Intent intent =new Intent(Signup.this,MainActivity.class);
        startActivity(intent);
    }
}