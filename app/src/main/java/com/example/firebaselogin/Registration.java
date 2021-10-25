package com.example.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    private EditText name, emailId, phone, password;
    Button registerBut;
    TextView loginBut;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();

        firebaseAuth = FirebaseAuth.getInstance();
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrName = name.getText().toString();
                String email = emailId.getText().toString();
                String phoneNumber = phone.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(usrName)) {
                    name.setError("Name can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailId.setError("Email Id can't be empty");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailId.setError("Invalid Email Id");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    phone.setError("Phone Number can't be empty");
                    return;
                }
                if (phoneNumber.length() != 10) {
                    phone.setError("Invalid Phone Number");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password can't be empty");
                    return;
                }
                if (pass.length() < 8) {
                    password.setError("Password should be atleast 8 character long");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginBut.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });
    }

    private void init() {
        name = findViewById(R.id.name);
        emailId = findViewById(R.id.emailId);
        phone = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.pass);
        registerBut = findViewById(R.id.signUp);
        loginBut = findViewById(R.id.newSignIn);
        progressBar = findViewById(R.id.progresBar);
    }
}