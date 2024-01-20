package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientBookingActivity extends AppCompatActivity {
    EditText patientName, patientTime, patientDate;
    Spinner diagnoses_list, doctors_list;
    Button booking_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_booking);
        patientDate = findViewById(R.id.patientDate);
        patientName = findViewById(R.id.PatientName);
        patientTime = findViewById(R.id.patientTime);
        diagnoses_list = findViewById(R.id.patientDiagnoses);
        doctors_list = findViewById(R.id.patientDoctor);
        booking_btn = findViewById(R.id.btnBook);

        // Populate the diagnoses spinner
        DatabaseReference diagnosesReference = FirebaseDatabase.getInstance().getReference().child("Diagnoses");
        diagnosesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> diagnoses_arr = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String option = snapshot.child("diagnosis_name").getValue(String.class);
                        diagnoses_arr.add(option);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(PatientBookingActivity.this, android.R.layout.simple_spinner_item, diagnoses_arr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    diagnoses_list.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Could Not Find Any Diagnoses List", Toast.LENGTH_SHORT).show();
            }
        });

        // Populate the doctors spinner
        DatabaseReference doctorsReference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        doctorsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> doctors_arr = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String option = snapshot.child("userName").getValue(String.class);
                        doctors_arr.add(option);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(PatientBookingActivity.this, android.R.layout.simple_spinner_item, doctors_arr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    doctors_list.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Could Not Find Any Doctors List", Toast.LENGTH_SHORT).show();
            }
        });

        booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve selected data
                String selectedDoctor = doctors_list.getSelectedItem().toString();
                String selectedDiagnosis = diagnoses_list.getSelectedItem().toString();
                String selectedDate = patientDate.getText().toString().trim();
                String selectedTime = patientTime.getText().toString().trim();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String patientId = currentUser.getUid();


                // Create an Appointment object
                Appointment appointment = new Appointment(patientId, selectedDoctor, selectedDiagnosis, selectedDate, selectedTime);

                // Store the appointment in Firebase
                DatabaseReference appointmentsReference = FirebaseDatabase.getInstance().getReference().child("Appointments");
                String appointmentKey = appointmentsReference.push().getKey();

                if (appointmentKey != null && !selectedDate.isEmpty() && !selectedTime.isEmpty() && !selectedDoctor.isEmpty() && !selectedDiagnosis.isEmpty()) {
                    appointmentsReference.child(appointmentKey).setValue(appointment);
                    Toast.makeText(getApplicationContext(), "Appointment added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add appointment, make sure all fields are entered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
