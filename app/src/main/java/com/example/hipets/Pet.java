package com.example.hipets;

import android.net.Uri;

public class Pet {

    //variables for storing our data.
    private int petId;
    private String petName, petAge, petSpecies, petSex, petSize, petBS, petLocation, petDesc, petNote, petUrl, petOwner, petOwnerEmail;

    public Pet() {
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public Pet(int petId, String petName, String petAge, String petSpecies, String petSex,
               String petSize, String petBS, String petLocation, String petDesc, String petNote, String petUrl,
               String petOwner, String petOwnerEmail) {
        this.petId = petId;
        this.petName = petName;
        this.petAge = petAge;
        this.petSpecies = petSpecies;
        this.petSex = petSex;
        this.petSize = petSize;
        this.petBS = petBS;
        this.petLocation = petLocation;
        this.petDesc = petDesc;
        this.petNote = petNote;
        this.petUrl = petUrl;
        this.petOwner = petOwner;
        this.petOwnerEmail = petOwnerEmail;
    }


    //Method get and setter
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getPetSize() {
        return petSize;
    }

    public void setPetSize(String petSize) {
        this.petSize = petSize;
    }

    public String getPetBS() {
        return petBS;
    }

    public void setPetBS(String petBS) {
        this.petBS = petBS;
    }

    public String getPetLocation() {
        return petLocation;
    }

    public void setPetLocation(String petLocation) {
        this.petLocation = petLocation;
    }

    public String getPetDesc() {
        return petDesc;
    }

    public void setPetDesc(String petDesc) {
        this.petDesc = petDesc;
    }

    public String getPetNote() {
        return petNote;
    }

    public void setPetNote(String petNote) {
        this.petNote = petNote;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetUrl() {
        return petUrl;
    }

    public void setPetUrl(String petUrl) {
        this.petUrl = petUrl;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(String petOwner) {
        this.petOwner = petOwner;
    }

    public String getPetOwnerEmail() {
        return petOwnerEmail;
    }

    public void setPetOwnerEmail(String petOwnerEmail) {
        this.petOwnerEmail = petOwnerEmail;
    }
}

