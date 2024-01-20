package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private List<Appointment> appointments;

    // Constructor to initialize the adapter with a list of appointments
    public AppointmentAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    // ViewHolder class to hold references to views in each item
    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private TextView doctorNameTextView;
        private TextView patientIDTextView;
        private TextView dateTextView;
        private TextView timeTextView;
        private TextView diagnosisTextView;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            doctorNameTextView = itemView.findViewById(R.id.doctorNameTextView);
            patientIDTextView = itemView.findViewById(R.id.patientIDTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            diagnosisTextView = itemView.findViewById(R.id.diagnosisTextView);
            // Initialize other views here if needed
        }

        public void bind(Appointment appointment) {
            doctorNameTextView.setText("Doctor: " + appointment.doctorName);
            patientIDTextView.setText("Patient ID: " + appointment.patientID);
            timeTextView.setText("Time: " + appointment.Time);
            dateTextView.setText("Date: " + appointment.Date);
            diagnosisTextView.setText("Diagnosis: " + appointment.diagnosisName);
            // Bind other appointment details here if needed
        }
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_appointment, parent, false);
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.bind(appointment);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
