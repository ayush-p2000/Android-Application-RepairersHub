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

public class Login extends AppCompatActivity {

    private EditText inputemail, inputpassword;
    private Button login;
    private TextView register, forgotpass;

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputemail = findViewById(R.id.userID);
        inputpassword = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgotpass = findViewById(R.id.forPassCus);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), Register.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ResetPassword.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });
    }

    private void PerformLogin() {
        String mail = inputemail.getText().toString();
        String password = inputpassword.getText().toString();

        if (!mail.matches(emailpattern))
        {
            inputemail.setError("Enter valid email");
        }
        else if (password.isEmpty() || password.length()<6)
        {
            inputpassword.setError("Enter valid password");
        }
        else {
            progressDialog.setMessage("Please wait while Logging in...");
            progressDialog.setTitle("Logging In");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        sendUserToNextActivity();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent in = new Intent(getApplicationContext(),Categories.class);
        startActivity(in);
    }
}