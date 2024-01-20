package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PatientRegisterActivity extends AppCompatActivity {
    EditText patient_name, patient_email, patient_password, patient_confirm_password;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        Button btn_register = findViewById(R.id.btnRegister);
        TextView txt_login = findViewById(R.id.txtLogin);
        patient_name = findViewById(R.id.registerName);
        patient_email = findViewById(R.id.registerEmail);
        patient_password = findViewById(R.id.registerPassword);
        patient_confirm_password = findViewById(R.id.registerConfirm);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register_btn_clicked(view);
            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientRegisterActivity.this, PatientLoginActivity.class));
                finish();
            }
        });
    }
    public void register_btn_clicked(View v){
        String txtFullName = patient_name.getText().toString().trim();
        String txtEmail = patient_email.getText().toString().trim();
        String txtPassword = patient_password.getText().toString().trim();
        String txtConfirm = patient_confirm_password.getText().toString().trim();

        if(txtFullName.isEmpty()){
            patient_name.setError("Please Enter Full Name");
            patient_name.requestFocus();
            return;
        }
        if(txtEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
            patient_email.setError("Please Enter A Valid Email");
            patient_email.requestFocus();
            return;
        }
        if(txtPassword.isEmpty() || txtPassword.length() < 6){
            patient_password.setError("Please Enter Password Of Atleast 6 Characters");
            patient_password.requestFocus();
            return;
        }
        if(txtConfirm.isEmpty() || !txtConfirm.equals(txtPassword)){
            patient_confirm_password.setError("Password Does Not Match");
            patient_confirm_password.requestFocus();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(txtFullName, txtPassword, txtEmail);

                                FirebaseDatabase.getInstance().getReference("Patients")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isComplete()) {
                                                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    startActivity(new Intent(PatientRegisterActivity.this, PatientLoginActivity.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });

                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

        }
    }
}