package com.example.hipets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class GivePetActivity extends AppCompatActivity {

    //create variables for petData
    private int petId;
    private String petName, petAge, petSpecies, petSex, petSize,
            petBS, petLocation, petDesc, petNote, petUrl, petOwner, petOwnerEmail;

    private ImageView imgPet, ivBackPressed, ivMenu;
    public Uri imageUri;
    private final int PICK_IMAGE_REQUEST = 22;

    //create variables for editText and button
    private EditText etPetName, etPetAge, etPetLocation, etPetDesc, etPetNote;
    private Spinner sPetSpecies, sPetSex, sPetSize, sPetBS;
    private Button btnGivePet;
    private TextView tvToAdoptPet;

    //create a variable for cloud Firestore.
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_pet);

        //back and menu
        ivBackPressed = findViewById(R.id.back_pressed);
        ivBackPressed.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });


        //pet image
        storage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        imgPet = findViewById(R.id.imgPet);
        imgPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectImage
                ImagePicker.with(GivePetActivity.this)
                        .crop()	    			    //Crop image(Optional), Check Customization for more option
//                        .compress(1024)			    //Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(1);

            }
        });

        //Spinner select species
        Spinner spinnerSpecies = (Spinner) findViewById(R.id.species_spinner);
        ArrayAdapter<CharSequence> adapterSpecies = ArrayAdapter.createFromResource(this,
                R.array.list_species, android.R.layout.simple_spinner_item);
        spinnerSpecies.setAdapter(adapterSpecies);
        spinnerSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Spinner select sex
        Spinner spinnerSex = (Spinner) findViewById(R.id.sex_spinner);
        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(this,
                R.array.list_sex, android.R.layout.simple_spinner_item);
        spinnerSex.setAdapter(adapterSex);
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner select size
        Spinner spinnerSize = (Spinner) findViewById(R.id.size_spinner);
        ArrayAdapter<CharSequence> adapterSize = ArrayAdapter.createFromResource(this,
                R.array.list_size, android.R.layout.simple_spinner_item);
        spinnerSize.setAdapter(adapterSize);
        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner select background story
        Spinner spinnerBS = (Spinner) findViewById(R.id.background_story_spinner);
        ArrayAdapter<CharSequence> adapterBS = ArrayAdapter.createFromResource(this,
                R.array.list_background_story, android.R.layout.simple_spinner_item);
        spinnerBS.setAdapter(adapterBS);
        spinnerBS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progressDialog = new ProgressDialog(GivePetActivity.this);
        progressDialog.setTitle("Uploading pet");
        progressDialog.setMessage("Please wait a moment");
        progressDialog.setCancelable(false);


        //Database
        db = FirebaseFirestore.getInstance();
        etPetName = findViewById(R.id.petName);
        etPetAge = findViewById(R.id.petAge);
        sPetSpecies = findViewById(R.id.species_spinner);
        sPetSex = findViewById(R.id.sex_spinner);
        sPetSize = findViewById(R.id.size_spinner);
        sPetBS = findViewById(R.id.background_story_spinner);
        etPetLocation = findViewById(R.id.petLocation);
        etPetDesc = findViewById(R.id.petDesc);
        etPetNote = findViewById(R.id.petNote);

        btnGivePet = findViewById(R.id.btn_givePet);

        //give pet to firebase
        btnGivePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                petName = etPetName.getText().toString();
                petAge = etPetAge.getText().toString();
                petSpecies = sPetSpecies.getSelectedItem().toString();
                petSex = sPetSex.getSelectedItem().toString();
                petSize = sPetSize.getSelectedItem().toString();
                petBS = sPetBS.getSelectedItem().toString();
                petLocation = etPetLocation.getText().toString();
                petDesc = etPetDesc.getText().toString();
                petNote = etPetNote.getText().toString();


                //validate the input fields
                if (TextUtils.isEmpty(petName)) {
                    etPetName.setError("Please enter pet name");
                } else if (TextUtils.isEmpty(petAge)) {
                    etPetAge.setError("Please enter pet age");
                } else if(petSpecies.equals("Select pet species")) {
                    ((TextView)sPetSpecies.getSelectedView()).setError("Please select pet species");
                } else if(petSex.equals("Select pet sex")) {
                    ((TextView)sPetSex.getSelectedView()).setError("Please select pet sex");
                } else if(petSize.equals("Select pet size")) {
                    ((TextView)sPetSize.getSelectedView()).setError("Please select pet size");
                } else if(petBS.equals("Select pet background")) {
                    ((TextView)sPetBS.getSelectedView()).setError("Please select pet background");
                } else if (TextUtils.isEmpty(petLocation)) {
                    etPetLocation.setError("Please enter pet location");
                } else if (TextUtils.isEmpty(petDesc)) {
                    etPetDesc.setError("Please enter pet description");
                } else if (TextUtils.isEmpty(petNote)) {
                    etPetNote.setError("Please enter pet note");
                } else {
                    //get data current user
                    getUser();

                    //call method to add data to Firebase Firestore
                    addPetToFirestore(petName, petAge, petSpecies, petSex, petSize, petBS, petLocation,
                                        petDesc, petNote, petUrl, petOwner, petOwnerEmail);

                    //clear data from edittext fields.
                    etPetName.setText("");
                    etPetAge.setText("");
                    sPetSpecies.setSelection(0);
                    sPetSex.setSelection(0);
                    sPetSize.setSelection(0);
                    sPetBS.setSelection(0);
                    etPetLocation.setText("");
                    etPetDesc.setText("");
                    etPetNote.setText("");

                    //start new activity
                    startActivity(new Intent(getApplicationContext(), FindPetActivity.class));
                }

            }

        });

        //Text view to find pet
        tvToAdoptPet = findViewById(R.id.tv_adopt_pet);
        tvToAdoptPet.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), FindPetActivity.class));
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 1 = image
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            imageUri = data.getData();
            imgPet.setImageURI(imageUri);
            //uploadImage
            uploadImage();
        }

    }

    //Method add pet data to Firestore
    private void addPetToFirestore(String petName, String petAge, String petSpecies, String petSex, String petSize,
                                   String petBS, String petLocation, String petDesc, String petNote, String petUrl,
                                   String petOwner, String petOwnerEmail) {

        progressDialog.show();

        //Increase petId
        petId++;

        Pet pet = new Pet(petId, petName, petAge, petSpecies, petSex, petSize, petBS,
                          petLocation, petDesc, petNote, petUrl, petOwner, petOwnerEmail);

        db.collection("pets").add(pet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressDialog.dismiss();
                Toast.makeText(GivePetActivity.this, "Your pet has been added for adoption", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(GivePetActivity.this, "Failed to give pet \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void uploadImage() {
        if (imageUri != null) {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading image");
            progressDialog.show();

            //Define the child of storageReference
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            ref.putFile(imageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(GivePetActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();

                                    //Get petUrl
                                    ref.getDownloadUrl()
                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    petUrl = uri.toString();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    // Handle any errors
                                                }
                                            });

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(GivePetActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

    private void getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            petOwner = user.getDisplayName();
            petOwnerEmail = user.getEmail();

        }
    }


}