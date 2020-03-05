package com.dev.furniturestore.AuthenticationActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.dev.furniturestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {
    private CardView Signup;
    private TextView Signin;
    private KProgressHUD hud;
    private FirebaseAuth mAuth;
    private EditText name, contact_no, email, password;
    private String Sname, Semail, Spassword, Scontact , abc,UserType;
    private Spinner spinner;
    private List<String> SpinnerElement;
    private ArrayAdapter<String> dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        findviews();
        intializeSpinnerAdapter();

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserType=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signup.this,Signin.class);
                startActivity(intent);
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmpty();
            }
        });
    }

    private void findviews() {
        Signup=findViewById(R.id.Signup);
        Signin=findViewById(R.id.Signin);
        name = findViewById(R.id.name);
        contact_no = findViewById(R.id.phoneno);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        spinner=findViewById(R.id.Spinner);

    }

    private void intializeSpinnerAdapter() {
        SpinnerElement=new ArrayList<String>();
        SpinnerElement.add("Customer");
        SpinnerElement.add("Seller");
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SpinnerElement);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void checkEmpty() {
        Sname = name.getText().toString();
        Semail = email.getText().toString();
        Spassword = password.getText().toString();
        Scontact = contact_no.getText().toString();

        if (Sname.isEmpty() || Semail.isEmpty() || Spassword.isEmpty() || Scontact.isEmpty()) {
            Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT).show();

        } else {
            if (Scontact.length() != 10) {
                Toast.makeText(this, "Mobile No is Not Valid", Toast.LENGTH_SHORT).show();
            } else {
                hud = KProgressHUD.create(Signup.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                hud.setProgress(90);
                abc = "?Name=" + Sname + "&Email=" + Semail+ "&ContactNo=" + Scontact + "&Password=" + Spassword+ "&UserType=" + UserType;
                validateEmail();
            }
        }
    }
    private void validateEmail() {
        mAuth.createUserWithEmailAndPassword(Semail, Spassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveData();
                        }else{
                            hud.dismiss();
                            Toast.makeText(Signup.this, "Wrong Details !!", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Signup.this, a1, Toast.LENGTH_SHORT).show();
                                hud.dismiss();
                                Intent intent=new Intent(Signup.this,Signin.class);
                                startActivity(intent);
                                mAuth.signOut();
                            } else {
                                String c1 = jo.getString("Error");
                                Toast.makeText(Signup.this, c1, Toast.LENGTH_SHORT).show();
                                hud.dismiss();
                                ReauthenticateFUser();
                            }
                        } catch (JSONException e) {
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }
    private void ReauthenticateFUser() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(Semail,Spassword);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        }
                                    }
                                });
                    }
                });
        mAuth.signOut();

    }
}
