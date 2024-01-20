package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorDiagnosesActivity extends AppCompatActivity {
    Button btnAdd;
    EditText txtDiagnosisName, txtDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_diagnoses);

        btnAdd = findViewById(R.id.btnAdd);
        txtDiagnosisName = findViewById(R.id.txtDiagnosisName);
        txtDescription = findViewById(R.id.txtDescription);

        //DatabaseReference for my Diagnosis node
        DatabaseReference medicationsRef = FirebaseDatabase.getInstance().getReference().child("Diagnoses");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diagnosisName = txtDiagnosisName.getText().toString().trim();
                String diagnosisDescription = txtDescription.getText().toString().trim();

                if (!diagnosisName.isEmpty() && !diagnosisDescription.isEmpty()) {
                    // Create a Diagnosis object with the provided data
                    Diagnosis diagnosis = new Diagnosis(diagnosisName, diagnosisDescription);

                    // Push the medication object to the Medications node
                    String key = medicationsRef.push().getKey();
                    medicationsRef.child(key).setValue(diagnosis);

                    // Clear the input fields after adding the medication
                    txtDiagnosisName.setText("");
                    txtDescription.setText("");

                    Toast.makeText(getApplicationContext(), "Diagnosis added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}