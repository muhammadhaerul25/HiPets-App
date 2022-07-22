package com.example.hipets;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ImageView imgFindPet;
    private ImageView imageMenu;
    private TextView tvUserName;
    private LinearLayout layoutFindPets, layoutGivePets, layoutPetInfo, layoutPetCare;

    private String userName, nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Shows username in TextView
        getUser();
        tvUserName = findViewById(R.id.textUsername);
        tvUserName.setText(userName);

        //Find Pets
        layoutFindPets = findViewById(R.id.layoutFindPets);
        layoutFindPets.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), FindPetActivity.class));
        });

        //Give Pets
        layoutGivePets = findViewById(R.id.layoutGivePets);
        layoutGivePets.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), GivePetActivity.class));
        });

        //Pet Info
        layoutPetInfo = findViewById(R.id.layoutPetInfo);
        layoutPetInfo.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PetInfoActivity.class));
        });

        //Pet Care
        layoutPetCare = findViewById(R.id.layoutPetCare);
        layoutPetCare.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PetCareActivity.class));
        });

        //Menu
        imageMenu = findViewById(R.id.imgMenu);
        imageMenu.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        });

    }

    private void getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userName = user.getDisplayName();
        }
    }
}