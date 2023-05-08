package com.example.smartschool.Activities.Dashboards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smartschool.Activities.Admin.ManageAdminActivity;
import com.example.smartschool.Activities.Admin.ManageStudentActivity;
import com.example.smartschool.R;

public class AdminDashboardActivity extends AppCompatActivity {

    CardView btnStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnStudent = findViewById(R.id.btnStudent);

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ManageStudentActivity.class));
            }
        });

        findViewById(R.id.btnManageAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ManageAdminActivity.class));

            }
        });
    }
}