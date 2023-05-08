package com.example.smartschool.Activities.Admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartschool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddAdminActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText txtName, txtStaffNo, txtPhone, txtEmail, txtPass, txtCPass;
    Spinner genderSpinner;
    String gender = "Male";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        txtName = findViewById(R.id.txtAdminName);
        txtStaffNo = findViewById(R.id.txtStaffNo);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        txtCPass = findViewById(R.id.txtCPass);
        genderSpinner = findViewById(R.id.genderSpinner);

        String[] gender_list = {"Male", "Female"};
        genderSpinner.setAdapter(new ArrayAdapter<String>(AddAdminActivity.this, android.R.layout.simple_spinner_dropdown_item, gender_list));

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gender = "Male";
                } else if (position == 1) {
                    gender = "Female";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        progressDialog = new ProgressDialog(AddAdminActivity.this);
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AddAdminActivity.this, AddAdminActivity.class));

                progressDialog.show();

                String name = txtName.getText().toString();
                String staffNo = txtStaffNo.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                String pass = txtPass.getText().toString();
                String cpass = txtCPass.getText().toString();
                String uid = db.collection("users").document("admins").collection("admins").document().getId();

                if (name.isEmpty() || staffNo.isEmpty() || phone.isEmpty() || email.isEmpty() || pass.isEmpty() || cpass.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(AddAdminActivity.this, "One of the fields is Empty", Toast.LENGTH_SHORT).show();

                } else if (!cpass.equals(pass)) {
                    progressDialog.dismiss();
                    Toast.makeText(AddAdminActivity.this, "The two passwords doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();

                    CollectionReference adminRef = db
                            .collection("users")
                            .document("admins")
                            .collection("admins");
                    Query query = adminRef.whereEqualTo("staffNo", staffNo);

                    query.get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        QuerySnapshot querySnapshot = task.getResult();

                                        if (querySnapshot != null && !querySnapshot.isEmpty()) {

                                            Toast.makeText(AddAdminActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();

                                        } else {

                                            Map<String, Object> admins = new HashMap<>();
                                            admins.put("name", name);
                                            admins.put("staffNo", staffNo);
                                            admins.put("phone", phone);
                                            admins.put("email", email);
                                            admins.put("pass", pass);
                                            admins.put("createdAt", new Timestamp(new Date()));

                                            db.collection("users")
                                                    .document("admins")
                                                    .collection("admins")
                                                    .document(uid)
                                                    .set(admins)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            //Log.d("SmartSchool", "Admin added with ID: " + uid);
                                                            Toast.makeText(AddAdminActivity.this, "Admin Added Successfully", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            //Log.w("SmartSchool", "Error adding admin", e);
                                                            Toast.makeText(AddAdminActivity.this, "Failed to Add Record", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                        }

                                    } else {
                                        Toast.makeText(AddAdminActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });
        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}