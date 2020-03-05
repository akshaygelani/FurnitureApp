package com.dev.furniturestore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.furniturestore.AuthenticationActivity.Splash;
import com.dev.furniturestore.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ShoppingMall extends AppCompatActivity {
    private ImageView addItem, Slidebar, cart;
    private DrawerLayout drawerLayout;
    private boolean doubleBackToExitPressedOnce = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_mall);
        mAuth = FirebaseAuth.getInstance();

        findViews();
        UpdateUi();
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingMall.this, AddItems.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingMall.this, Cart.class);
                startActivity(intent);
            }
        });
        Slidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationdrawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.laborwork) {
                    Intent intent = new Intent(ShoppingMall.this, LaborWork.class);
                    startActivity(intent);
                } else if (id == R.id.home) {
                    Intent intent = new Intent(ShoppingMall.this, Home.class);
                    startActivity(intent);
                } else if (id == R.id.shoppingmall) {
                    Intent intent = new Intent(ShoppingMall.this, ShoppingMall.class);
                    startActivity(intent);
                } else if (id == R.id.cart) {
                    Intent intent = new Intent(ShoppingMall.this, Cart.class);
                    startActivity(intent);
                } else if (id == R.id.requests) {
                    Intent intent = new Intent(ShoppingMall.this, EstimationRequests.class);
                    startActivity(intent);
                } else if (id == R.id.share) {
                    Toast.makeText(ShoppingMall.this, "Share", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.about) {
                    Toast.makeText(ShoppingMall.this, "About", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.setting) {
                    Toast.makeText(ShoppingMall.this, "Setting", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.logout) {
                    mAuth.signOut();
                    Toast.makeText(ShoppingMall.this, "You Have Been Logout!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ShoppingMall.this, Splash.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    private void UpdateUi() {
        SharedPreferences data = ShoppingMall.this.getSharedPreferences("CRED", MODE_PRIVATE);
        String value = data.getString("Type", "Data not found");
        if (value.equals("Customer")) {
            addItem.setVisibility(View.INVISIBLE);
        }
    }

    private void findViews() {
        Slidebar = findViewById(R.id.Slidebar);
        addItem = findViewById(R.id.addItems);
        drawerLayout = findViewById(R.id.homeDrawer);
        cart = findViewById(R.id.cart);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
