package com.example.hipets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private Button btnLogout;
    private ImageView imgBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        progressDialog = new ProgressDialog(MenuActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait a moment");
        progressDialog.setCancelable(false);

        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            logout();
        });

        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

    }

    public void logout() {
        progressDialog.show();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
        progressDialog.dismiss();
    }
}