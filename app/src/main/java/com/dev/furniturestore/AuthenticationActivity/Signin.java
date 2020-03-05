package com.dev.furniturestore.AuthenticationActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.dev.furniturestore.Activity.Home;
import com.dev.furniturestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Signin extends AppCompatActivity {

    private CardView Signin;
    private TextView Signup;
    private EditText email, password;
    private String Semail, Spassword, abc;
    private KProgressHUD hud;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();

        findviews();
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this, Signup.class);
                startActivity(intent);
            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmpty();
            }
        });
    }

    private void findviews() {
        Signin = findViewById(R.id.Signin);
        Signup = findViewById(R.id.Signup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    private void checkEmpty() {
        Semail = email.getText().toString();
        Spassword = password.getText().toString();
        if (Semail.isEmpty() || Spassword.isEmpty()) {
            Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT).show();

        } else {
            hud = KProgressHUD.create(Signin.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            hud.setProgress(90);
            abc = "?Email=" + Semail + "&Password=" + Spassword;
            signin();
        }
    }

    private void signin() {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveData();
                        } else {
                            hud.dismiss();
                            Toast.makeText(Signin.this, "Enter Valid Credential", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void saveData() {
        String URL="YOUR API";
        Log.e("URL",URL+abc);
        AndroidNetworking.get(URL + abc)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jo;
                        try {
                            jo = response.getJSONObject(0);
                            if (jo.has("Success")) {
                                String a1 = jo.getString("Success");
                                SharedPreferences sharedPreferences=Signin.this.getSharedPreferences("CRED",MODE_PRIVATE);
                                SharedPreferences.Editor editore=sharedPreferences.edit();
                                editore.putString("Auth",jo.getString("Auth"));
                                editore.putString("UID",jo.getString("UID"));
                                editore.putString("NAME",jo.getString("Name"));
                                editore.putString("Type",jo.getString("Type"));
                                editore.apply();

                                Toast.makeText(Signin.this, a1, Toast.LENGTH_SHORT).show();

                                hud.dismiss();
                                Intent intent=new Intent(Signin.this, Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String c1 = jo.getString("Error");
                                Toast.makeText(Signin.this, c1, Toast.LENGTH_SHORT).show();
                                hud.dismiss();
                                mAuth.signOut();
                            }
                        } catch (JSONException e) {
                            Log.e("EXCEPTION",String.valueOf(e));
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("EXCEPTIONERROR",String.valueOf(anError));

                    }
                });
    }
}
