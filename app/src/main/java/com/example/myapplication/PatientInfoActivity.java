package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientInfoActivity extends AppCompatActivity {

    TextView patientName, patientEmail;
    EditText updateName;
    Button update_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        patientName = findViewById(R.id.patientName);
        patientEmail = findViewById(R.id.patientEmail);
        updateName = findViewById(R.id.txtUpdateName);
        update_btn = findViewById(R.id.btnUpdate);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.child("userName").getValue(String.class);
                    String userEmail = dataSnapshot.child("email").getValue(String.class);
                    patientName.setText(userName);
                    patientEmail.setText(userEmail);

                } else {
                    Toast.makeText(getApplicationContext(), "Username/Email Column Could Not Be Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_name = updateName.getText().toString().trim();
                if(new_name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter A Name Before Updating", Toast.LENGTH_SHORT).show();
                }
                else{
                    userRef.child("userName").setValue(new_name);
                    Toast.makeText(getApplicationContext(), "Name Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });






    }
}