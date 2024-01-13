package com.example.repairershubofficial;

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

public class ResetPassword extends AppCompatActivity {
    private EditText emailInput;
    private Button reset;
    private FirebaseAuth mAuth;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailInput = findViewById(R.id.resetEmail);
        reset = findViewById(R.id.resetButton);
        mAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailInput.getText().toString().trim();

        if(email.isEmpty())
        {
            emailInput.setError("Email required");
            emailInput.requestFocus();
            return;
        }
        if (!email.matches(emailpattern))
        {
            emailInput.setError("Please enter valid email");
            emailInput.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ResetPassword.this, "Please check your email and follow the link.", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),Login.class);
                    startActivity(in);
                }
                else
                {
                    Toast.makeText(ResetPassword.this, "Something went wrong, Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}