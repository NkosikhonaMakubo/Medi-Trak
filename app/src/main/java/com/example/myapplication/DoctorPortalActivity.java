package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DoctorPortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_portal);
        LinearLayout doctor_tab, medicine_tab, diagnoses_tab, appointment_tab;

        doctor_tab = findViewById(R.id.doctorInfoTab);
        medicine_tab = findViewById(R.id.medicineTab);
        diagnoses_tab = findViewById(R.id.diagnosesTab);
        appointment_tab = findViewById(R.id.appointmentsTab);

        doctor_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorPortalActivity.this, DoctorInfoActivity.class));
            }
        });
        medicine_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorPortalActivity.this, DoctorMedicationActivity.class));
            }
        });
        diagnoses_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorPortalActivity.this, DoctorDiagnosesActivity.class));
            }
        });
        appointment_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorPortalActivity.this, DoctorAppointmentsActivity.class));
            }
        });
    }
}