package com.example.hipets;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsPetActivity extends AppCompatActivity {

    private TextView tvPetName, tvPetAge, tvPetSpecies, tvPetSex, tvPetSize, tvPetBS,
            tvPetLocation, tvPetDesc, tvPetNote, tvPetOwner, tvPetOwnerEmail;
    private ImageView ivPetImg, backPressed;
    private Button btnAdoptPet;
    private String petName, petAge, petSpecies, petSex, petSize, petBS, petLocation, petDesc, petNote, petUrl, petOwner, petOwnerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_pet);

        //back and three dots
        backPressed = findViewById(R.id.img_back);
        backPressed.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), FindPetActivity.class));
        });


        //find tv
        tvPetName = findViewById(R.id.petName);
        tvPetAge = findViewById(R.id.petAge);
        tvPetSpecies = findViewById(R.id.petSpecies);
        tvPetSex = findViewById(R.id.petSex);
        tvPetSize = findViewById(R.id.petSize);
        tvPetBS = findViewById(R.id.petBS);
        tvPetLocation = findViewById(R.id.petLocation);
        tvPetDesc = findViewById(R.id.petDesc);
        tvPetNote = findViewById(R.id.petNote);
        ivPetImg = findViewById(R.id.petImg);

        //set data to tv and iv
        getIncomingIntent();
        Picasso.get().load(petUrl).into(ivPetImg);
        tvPetName.setText(petName);
        tvPetAge.setText(petAge);
        tvPetSpecies.setText(petSpecies);
        tvPetSex.setText(petSex);
        tvPetSize.setText(petSize);
        tvPetBS.setText(petBS);
        tvPetLocation.setText(petLocation);
        tvPetDesc.setText(petDesc);
        tvPetNote.setText(petNote);

        //btn adopt pet
        btnAdoptPet = findViewById(R.id.btnAdoptPet);
        btnAdoptPet.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AdoptPetActivity.class));
        });



    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        petName = getIntent().getStringExtra("petName");
        petAge = getIntent().getStringExtra("petAge");
        petSpecies = getIntent().getStringExtra("petSpecies");
        petSex = getIntent().getStringExtra("petSex");
        petSize = getIntent().getStringExtra("petSize");
        petBS = getIntent().getStringExtra("petBS");
        petLocation = getIntent().getStringExtra("petLocation");
        petDesc = getIntent().getStringExtra("petDesc");
        petNote = getIntent().getStringExtra("petNote");
        petUrl = getIntent().getStringExtra("petUrl");
        petOwner = getIntent().getStringExtra("petOwner");
        petOwnerEmail = getIntent().getStringExtra("petOwnerEmail");


    }

}