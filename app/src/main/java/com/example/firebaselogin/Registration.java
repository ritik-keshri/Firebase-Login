package com.example.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    private EditText pass, emailId;
    Button button;
    TextView textView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();

        firebaseAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(c -> {
                if (!emailId.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString()).matches() && !pass.getText().toString().isEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(emailId.getText().toString(), pass.getText().toString()).addOnCompleteListener(Registration.this, (OnCompleteListener<AuthResult>) task -> {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Already have account", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid id or password", Toast.LENGTH_SHORT).show();
                }
        });

        textView.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });
    }

    private void init() {
        textView = findViewById(R.id.newSignIn);
        pass = findViewById(R.id.pass);
        emailId = findViewById(R.id.emailId);
        button = findViewById(R.id.signUp);
    }
}