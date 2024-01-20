package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientAppointmentsActivity extends AppCompatActivity {
    String currentPatientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointments);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String userUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference userReference = databaseReference.child("Patients").child(userUid);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentPatientName = dataSnapshot.child("userName").getValue(String.class);
                } else {
                    Toast.makeText(getApplicationContext(), "Could Not Find UserName", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Appointments");
        String patientName = currentPatientName;
        Query query = databaseReference.orderByChild("userName").equalTo(patientName);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Appointment> patientAppointments = new ArrayList<>();

                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {

                    Appointment appointment = appointmentSnapshot.getValue(Appointment.class);

                    patientAppointments.add(appointment);
                }

                RecyclerView recyclerView = findViewById(R.id.patientAppointmentsRecyclerview);
                AppointmentAdapter adapter = new AppointmentAdapter(patientAppointments);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}