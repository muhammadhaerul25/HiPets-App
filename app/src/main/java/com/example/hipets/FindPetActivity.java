package com.example.hipets;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FindPetActivity extends AppCompatActivity {

    private ImageView imgBack, imgPet;
    private FirebaseFirestore db;
    private List<FindPet> findPets = new ArrayList<>();
    private FindPetsAdapter petAdapter;

    private CardView petCardView;
    private Boolean clickPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pet);

        //back and menu
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        ViewPager2 locationsViewPager = findViewById(R.id.locationsViewPager);

        //Make adapter
        petAdapter = new FindPetsAdapter(this, findPets);

        //read data from FireStore
        db = FirebaseFirestore.getInstance();
        db.collection("pets").orderBy("petId", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        findPets.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document: task.getResult()) {
                                FindPet pet = new FindPet();
                                pet.petName = document.getString("petName");
                                pet.petAge = document.getString("petAge");
                                pet.petSpecies = document.getString("petSpecies");
                                pet.petSex = document.getString("petSex");
                                pet.petSize = document.getString("petSize");
                                pet.petBS = document.getString("petBS");
                                pet.petLocation = document.getString("petLocation");
                                pet.petDesc = document.getString("petDesc");
                                pet.petNote = document.getString("petNote");
                                pet.petUrl = document.getString("petUrl");
                                pet.petOwner = document.getString("petOwner");
                                pet.petOwnerEmail = document.getString("petOwnerEmail");
                                findPets.add(pet);
                            }
                            petAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


        locationsViewPager.setAdapter(petAdapter);


        locationsViewPager.setClipToPadding(false);
        locationsViewPager.setClipChildren(false);
        locationsViewPager.setOffscreenPageLimit(3);
        locationsViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        locationsViewPager.setPageTransformer(compositePageTransformer);


        //swipe viewpager2
        // To get swipe event of viewpager2
        locationsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            // This method is triggered when there is any scrolling activity for the current page
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            // triggered when you select a new page
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);


            }

            // triggered when there is scroll state will be changed
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }


        });




    }

    private void getPet() {

    }


}