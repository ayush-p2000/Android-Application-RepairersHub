package com.example.repairershubofficial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class categoryDetails extends AppCompatActivity {
    private EditText completeAddress, emailCheckout, problem, contact, name;
    private CheckBox agreement;
    private Button checkout;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String service;
    FirebaseDatabase database;
    DatabaseReference Mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        completeAddress = findViewById(R.id.address);
        emailCheckout = findViewById(R.id.emailCheckout);
        problem = findViewById(R.id.acProblem);
        contact = findViewById(R.id.ac_contact);
        agreement = findViewById(R.id.ac_checkBox);
        checkout = findViewById(R.id.checkoutACbutton);
        name = findViewById(R.id.fullnameCustomer);

        Intent in = getIntent();
        service = in.getStringExtra("SERVICE");

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = completeAddress.getText().toString().trim();
                String email = emailCheckout.getText().toString().trim();
                String pDescrip = problem.getText().toString().trim();
                String number = contact.getText().toString().trim();
                String fname = name.getText().toString().trim();

                if (fname.isEmpty()) {
                    name.setError("Field is required");
                    name.requestFocus();
                    return;
                } else if (address.isEmpty()) {
                    completeAddress.setError("Field is required");
                    completeAddress.requestFocus();
                    return;
                } else if (email.isEmpty()) {
                    emailCheckout.setError("Field is required");
                    emailCheckout.requestFocus();
                    return;
                } else if (!email.matches(emailpattern)) {
                    Toast.makeText(categoryDetails.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else if (pDescrip.isEmpty()) {
                    problem.setError("Field is required");
                    problem.requestFocus();
                    return;
                } else if (number.isEmpty()) {
                    contact.setError("Field is required");
                    contact.requestFocus();
                    return;
                } else if (number.length() != 10) {
                    Toast.makeText(categoryDetails.this, "Please check your number", Toast.LENGTH_SHORT).show();
                } else if (!agreement.isChecked()) {
                    Toast.makeText(categoryDetails.this, "Please agree to the checkbox", Toast.LENGTH_SHORT).show();
                } else {
                    FetchDetails fd = new FetchDetails(fname, address, email, number, pDescrip, service);
                    database = FirebaseDatabase.getInstance();
                    Mydb = database.getReference("Users");
                    Mydb.child(number).setValue(fd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(categoryDetails.this, "Details Generated", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getApplicationContext(), com.example.repairershubofficial.checkout.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                in.putExtra("NUMBER", number);
                                in.putExtra("NAME", fname);
                                in.putExtra("ADDRESS", address);
                                in.putExtra("EMAIL", email);
                                in.putExtra("DES",pDescrip);
                                in.putExtra("SERVICE", service);
                                startActivity(in);
                            } else {
                                Toast.makeText(categoryDetails.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
