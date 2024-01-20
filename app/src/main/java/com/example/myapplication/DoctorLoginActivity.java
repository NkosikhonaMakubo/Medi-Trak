package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorLoginActivity extends AppCompatActivity {
    EditText loginEmail, loginPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        Button btn_login = findViewById(R.id.btnRegister);
        TextView sign_up = findViewById(R.id.txtRegister);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        mAuth = FirebaseAuth.getInstance();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorLoginActivity.this, DoctorRegisterActivity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_user(view);
            }
        });

    }
    public void check_user(View v){
        String login_email = loginEmail.getText().toString().trim();
        String login_password = loginPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(login_email, login_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Check if the user is a doctor
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference doctorsReference = FirebaseDatabase.getInstance().getReference().child("Doctors");
                            doctorsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(userId)) {
                                        // The user is a doctor, proceed to the doctor's portal
                                        Toast.makeText(getApplicationContext(), "Login Successfully as Doctor", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(DoctorLoginActivity.this, DoctorPortalActivity.class));
                                        finish();
                                    } else {
                                        // The user is not a doctor
                                        Toast.makeText(getApplicationContext(), "You are not a doctor", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), "An Error Has Occurred", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            if ("ERROR_USER_NOT_FOUND".equals(errorCode)) {
                                Toast.makeText(getApplicationContext(), "User Does Not Exist", Toast.LENGTH_SHORT).show();
                            } else if ("ERROR_WRONG_PASSWORD".equals(errorCode)) {
                                loginPassword.setError("Incorrect Password");
                                loginPassword.requestFocus();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Error: " + errorCode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}