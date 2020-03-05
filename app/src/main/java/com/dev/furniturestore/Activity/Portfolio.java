package com.dev.furniturestore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dev.furniturestore.R;

public class Portfolio extends AppCompatActivity {
CardView estimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        estimation=findViewById(R.id.estimation);
        estimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Portfolio.this,SelectFurnitureType.class));
            }
        });
    }
}
