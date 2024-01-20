package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorMedicationActivity extends AppCompatActivity {
    Button btnAdd;
    EditText txtMedicationName, txtDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_medication);

        btnAdd = findViewById(R.id.btnAdd);
        txtMedicationName = findViewById(R.id.txtMedicationName);
        txtDescription = findViewById(R.id.txtDescription);

        //DatabaseReference for my Medications node
        DatabaseReference medicationsRef = FirebaseDatabase.getInstance().getReference().child("Medications");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medicationName = txtMedicationName.getText().toString().trim();
                String medicationDescription = txtDescription.getText().toString().trim();

                if (!medicationName.isEmpty() && !medicationDescription.isEmpty()) {
                    // Create a Medication object with the provided data
                    Medication medication = new Medication(medicationName, medicationDescription);

                    // Push the medication object to the Medications node
                    String key = medicationsRef.push().getKey();
                    medicationsRef.child(key).setValue(medication);

                    // Clear the input fields after adding the medication
                    txtMedicationName.setText("");
                    txtDescription.setText("");

                    Toast.makeText(getApplicationContext(), "Medication added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}