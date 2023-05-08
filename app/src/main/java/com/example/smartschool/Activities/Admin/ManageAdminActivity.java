package com.example.smartschool.Activities.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartschool.Adapters.AdminAdapter;
import com.example.smartschool.Models.AdminModel;
import com.example.smartschool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ManageAdminActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<AdminModel> adminModels=new ArrayList<>();
    AdminAdapter adminAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admin);

        progressDialog=new ProgressDialog(ManageAdminActivity.this);
        progressDialog.setMessage("Getting Records...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView=findViewById(R.id.adminRecyclerVew);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageAdminActivity.this));
         adminAdapter=new AdminAdapter(adminModels,ManageAdminActivity.this);
        getAdmins();

        findViewById(R.id.btnAddAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageAdminActivity.this, AddAdminActivity.class));
            }
        });
        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchArrayList(newText);
                return false;
            }
        });
    }
    private void searchArrayList(String newText) {
        ArrayList<AdminModel> dataSearchArrayList = new ArrayList<>();

        for (AdminModel data : adminModels) {
            if (data.getName().toLowerCase().contains(newText.toLowerCase())) {
                dataSearchArrayList.add(data);
            }
        }

        if (dataSearchArrayList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adminAdapter.setSearchList(dataSearchArrayList);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAdmins();
    }

    private void getAdmins() {
        progressDialog.dismiss();

        db.collection("users").document("admins").collection("admins").orderBy("createdAt")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        adminModels.clear();

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.d("SmartSchool", document.getId() + " => " + document.getData());
                                AdminModel getData = document.toObject(AdminModel.class);
                                adminModels.add(getData);
                                recyclerView.setAdapter(adminAdapter);

                               // Log.d("SmartSchool", document.getId() + " => " + getData.getName());

                            }
                        } else {
                            Toast.makeText(ManageAdminActivity.this, "Error getting Records", Toast.LENGTH_SHORT).show();
                            Log.w("SmartSchool", "Error getting documents.", task.getException());
                        }

                    }
                });
    }
}