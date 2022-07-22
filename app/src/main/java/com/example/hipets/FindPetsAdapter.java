package com.example.hipets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class FindPetsAdapter extends RecyclerView.Adapter<FindPetsAdapter.FindPetViewHolder> {

    private List<FindPet> findPets;
    private Context petContext;

    public FindPetsAdapter(Context context, List<FindPet> findPets) {
        this.findPets = findPets;
        petContext = context;
    }

    @NonNull
    @Override
    public FindPetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FindPetViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_location,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FindPetViewHolder holder, int position) {
        holder.setLocationData(findPets.get(position));

        holder.imgPet.setOnClickListener((view) ->{
            Intent intent = new Intent(petContext, DetailsPetActivity.class);
            intent.putExtra("petName" ,findPets.get(position).petName);
            intent.putExtra("petAge" ,findPets.get(position).petAge);
            intent.putExtra("petSpecies" ,findPets.get(position).petSpecies);
            intent.putExtra("petSex" ,findPets.get(position).petSex);
            intent.putExtra("petSize" ,findPets.get(position).petSize);
            intent.putExtra("petBS" ,findPets.get(position).petBS);
            intent.putExtra("petLocation" ,findPets.get(position).petLocation);
            intent.putExtra("petDesc" ,findPets.get(position).petDesc);
            intent.putExtra("petNote" ,findPets.get(position).petNote);
            intent.putExtra("petUrl" ,findPets.get(position).petUrl);
            intent.putExtra("petOwner" ,findPets.get(position).petOwner);
            intent.putExtra("petOwnerEmail" ,findPets.get(position).petOwnerEmail);
            petContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return findPets.size();
    }

    static class FindPetViewHolder extends RecyclerView.ViewHolder {

        //private KenBurnsView kbvLocation;
        private TextView textTitle, textLocation, textStarRating;
        private ImageView imgPet;


        FindPetViewHolder(@NonNull View itemView) {
            super(itemView);
            //kbvLocation = itemView.findViewById(R.id.kbvLocation);
            imgPet = itemView.findViewById(R.id.imgPet);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLocation = itemView.findViewById(R.id.textLocation);
            textStarRating = itemView.findViewById(R.id.textStarRating);
        }

        void setLocationData(FindPet findPet) {
            Picasso.get().load(findPet.petUrl).into(imgPet);
            //Glide.with(FindPetActivity.class).load(findPet.imageUrl).into(imgPet);
            textTitle.setText(findPet.petName);
            textLocation.setText(findPet.petLocation);
            textStarRating.setText(String.valueOf(findPet.petSpecies));
        }
    }
}
