package com.dev.furniturestore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dev.furniturestore.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LaborWork extends AppCompatActivity {
    CardView Seller1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_work);
        Seller1=findViewById(R.id.Seller1);
        Seller1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaborWork.this,Portfolio.class));
            }
        });
    }
}
