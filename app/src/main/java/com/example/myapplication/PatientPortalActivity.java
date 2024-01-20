package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PatientPortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_portal2);

        LinearLayout booking_tab, appointment_tab, patient_info_tab, reports_tab;

        booking_tab = findViewById(R.id.bookingTab);
        appointment_tab = findViewById(R.id.appointmentsTab);
        patient_info_tab = findViewById(R.id.patientInfoTab);
        reports_tab = findViewById(R.id.reportsTab);

        booking_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientPortalActivity.this, PatientBookingActivity.class));
            }
        });

        appointment_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientPortalActivity.this, PatientAppointmentsActivity.class));
            }
        });

        patient_info_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientPortalActivity.this, PatientInfoActivity.class));
            }
        });

        reports_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Records Tab Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}