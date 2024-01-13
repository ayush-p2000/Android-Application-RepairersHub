package com.example.repairershubofficial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private TextView redirect;
    private EditText fname, email, pass, cpass;
    private Button submit;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        cpass = findViewById(R.id.confPass);
        submit = findViewById(R.id.submit);
        redirect = findViewById(R.id.redirectLogin);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),Login.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });
    }

    private void PerformAuth() {
        String fullname = fname.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String confpass = cpass.getText().toString().trim();

        if (fullname.isEmpty())
        {
            fname.setError("Full Name is required");
        }
        if (!mail.matches(emailpattern))
        {
            email.setError("Enter valid email");
            email.requestFocus();
        }
        else if (password.isEmpty() || password.length()<6)
        {
            pass.setError("Enter valid password");
            pass.requestFocus();
        }
        else if (!password.equals(confpass))
        {
            cpass.setError("Passwords doesn't match");
            cpass.requestFocus();
        }
        else if (fullname.isEmpty())
        {
            fname.setError("Full name is required");
            fname.requestFocus();
        }
        else
        {
            progressDialog.setMessage("Please wait while registering");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        sendUserToNextActivity();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, "Email already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent in = new Intent(getApplicationContext(),Login.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }
}