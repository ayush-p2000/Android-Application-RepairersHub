package com.example.repairershubofficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Categories extends AppCompatActivity {
    private Button ac, pc, mobile, fridge, wm, others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ac = findViewById(R.id.ac);
        pc = findViewById(R.id.pcomputer);
        mobile = findViewById(R.id.phone);
        fridge = findViewById(R.id.fridge);
        wm = findViewById(R.id.wm);
        others = findViewById(R.id.others);

        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), categoryDetails.class);
                in.putExtra("SERVICE", "Air Conditioner Service");
                startActivity(in);
            }
        });
        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), categoryDetails.class);
                in.putExtra("SERVICE", "Computer Service");
                startActivity(in);
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), categoryDetails.class);
                in.putExtra("SERVICE", "Mobile Service");
                startActivity(in);
            }
        });
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), categoryDetails.class);
                in.putExtra("SERVICE", "Refrigerator Service");
                startActivity(in);
            }
        });
        wm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), categoryDetails.class);
                in.putExtra("SERVICE", "Washing Machine Service");
                startActivity(in);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Categories.this, "Sorry! Under Development...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}