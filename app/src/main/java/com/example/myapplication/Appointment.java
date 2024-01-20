package com.example.myapplication;

public class Appointment {
    public String patientID;
    public String doctorName;
    public String diagnosisName;
    public String Date;
    public String Time;

    public Appointment(){

    }

    public Appointment(String patient_Id, String doctor_name, String diagnosis_name, String date, String time){
        this.patientID = patient_Id;
        this.doctorName = doctor_name;
        this.diagnosisName = diagnosis_name;
        this.Date = date;
        this.Time = time;
    }
/*
    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getTime() {
        return Time;
    }

    public String getDate() {
        return Date;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }*/
}
