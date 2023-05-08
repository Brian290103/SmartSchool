package com.example.smartschool.Models;

import com.google.firebase.Timestamp;

public class AdminModel {

    String email, name, pass, phone, staffNo,  image;
    Timestamp createdAt;

    public AdminModel() {
    }

    public AdminModel(String email, String name, String pass, String phone, String staffNo, Timestamp createdAt, String image) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.staffNo = staffNo;
        this.createdAt = createdAt;

      /*  Date date = createdAt.toDate();
        //  this.createdAt = createdAt;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdAt = sdf.format(date);
        this.image = image;
       */
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getPhone() {
        return phone;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getimage() {
        return image;
    }
}
