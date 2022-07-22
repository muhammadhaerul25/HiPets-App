package com.example.hipets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private EditText editName, editEmail, editPhoneNumber, editPassword, editPasswordConf;
    private Button btnRegister;
    private TextView tvLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.name);
        editEmail = findViewById(R.id.email);
        editPhoneNumber = findViewById(R.id.phone_number);
        editPassword = findViewById(R.id.password);
        editPasswordConf = findViewById(R.id.password_conf);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait a moment");
        progressDialog.setCancelable(false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {
            if (editName.getText().length()>0 && editEmail.getText().length()>0 && editPhoneNumber.getText().length()>0 && editPassword.getText().length()>0 && editPasswordConf.getText().length()>0) {
                if (editPassword.getText().toString().equals(editPasswordConf.getText().toString())) {
                    register(editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter the same password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please complete the data", Toast.LENGTH_SHORT).show();
            }
        });

        tvLogin.setOnClickListener(v -> {
            finish();
        });
    }

    private void register(String name, String email, String password ) {
        progressDialog.show();
        //Register code
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && task.getResult().getUser()!=null) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            if (firebaseUser!=null) {
                                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                                firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        reload();
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void reload() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}