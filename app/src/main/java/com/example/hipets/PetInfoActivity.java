package com.example.hipets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class PetInfoActivity extends AppCompatActivity {

    private ImageView ivBackPressed, ivMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        //back and menu
        ivBackPressed = findViewById(R.id.back_pressed);
        ivBackPressed.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }
}