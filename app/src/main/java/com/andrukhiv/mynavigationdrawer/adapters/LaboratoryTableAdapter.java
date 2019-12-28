package com.andrukhiv.mynavigationdrawer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.models.LaboratoryTableModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class LaboratoryTableAdapter extends RecyclerView.Adapter<LaboratoryTableAdapter.MyRecyclerViewHolder> {

    private List<LaboratoryTableModel> laboratoryTableModelList;
    private Context mContext;

    public LaboratoryTableAdapter(List<LaboratoryTableModel> laboratoryModelList, Context mContext) {

        this.laboratoryTableModelList = laboratoryModelList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.laboratory_table_row, null);
        return new MyRecyclerViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {

        holder.fortress_wine.setText(laboratoryTableModelList.get(position).getFortress_future_wine());
        holder.specific_juice.setText(laboratoryTableModelList.get(position).getSpecific_gravity_juice());
        holder.sugar_juice.setText(laboratoryTableModelList.get(position).getSugar_content_juice());

        if (position % 2 == 1) {
            holder.layout_content_view.setBackgroundColor(mContext.getResources().getColor(R.color.rowBackground));
        } else {
            holder.layout_content_view.setBackgroundColor(mContext.getResources().getColor(R.color.windowBackground));
        }
    }


    @Override
    public int getItemCount() {
        if (laboratoryTableModelList.size() > 0)
            return laboratoryTableModelList.size();
        else
            return 0;
    }


    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView specific_juice, sugar_juice, fortress_wine;
        LinearLayoutCompat layout_content_view;

        MyRecyclerViewHolder(View itemView) {

            super(itemView);
            specific_juice = itemView.findViewById(R.id.specific_gravity_juice);
            sugar_juice = itemView.findViewById(R.id.sugar_content_juice);
            fortress_wine = itemView.findViewById(R.id.fortress_future_wine);

            layout_content_view = itemView.findViewById(R.id.layout_content_view);
        }
    }
}
