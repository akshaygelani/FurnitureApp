package com.dev.furniturestore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dev.furniturestore.R;

public class SelectFurnitureType extends AppCompatActivity {

    CardView kitchen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_furniture_type);
        kitchen=findViewById(R.id.kitchen);
        kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectFurnitureType.this,SelectKitchenType.class));
            }
        });
    }
}
