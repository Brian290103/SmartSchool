package com.example.smartschool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartschool.Models.AdminModel;
import com.example.smartschool.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {
    ArrayList<AdminModel> adminModels;
    final Context context;


    public AdminAdapter(ArrayList<AdminModel> adminModels, Context context) {
        this.adminModels = adminModels;
        this.context = context;
    }

    public void setSearchList(ArrayList<AdminModel> adminModels) {
        this.adminModels = adminModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.MyViewHolder holder, int position) {

        AdminModel currentItem = adminModels.get(position);

        holder.txtName.setText(currentItem.getName());
        holder.txtStaffNo.setText(currentItem.getStaffNo());
        holder.txtEmail.setText(currentItem.getEmail());
        if (currentItem.getimage() != null) {
            Picasso.get().load(currentItem.getimage()).into(holder.profileImg);
        }else{holder.profileImg.setImageResource(R.drawable.profile);
        }

    }

    @Override
    public int getItemCount() {
        return adminModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtStaffNo, txtEmail;
        ImageView profileImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtStaffNo = itemView.findViewById(R.id.txtStaffNo);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            profileImg = itemView.findViewById(R.id.profileImg);

        }
    }
}
