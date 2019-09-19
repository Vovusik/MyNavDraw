package com.andrukhiv.mynavigationdrawer.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.models.KitchenModel;

import java.util.ArrayList;
import java.util.List;

public class KitchenRecyclerAdapter extends RecyclerView.Adapter<KitchenRecyclerAdapter.DataObjectHolder> {

    private static String LOG_TAG = "RecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private Context context;

    protected ArrayList<KitchenModel> kitchenModels;


    public KitchenRecyclerAdapter(ArrayList<KitchenModel> myDataset) {
        kitchenModels = new ArrayList<>(myDataset);
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView textName;

        public DataObjectHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(KitchenRecyclerAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.textName.setText(kitchenModels.get(position).getName());
    }


    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kitchen_item, parent, false);

        context = parent.getContext();

        return new DataObjectHolder(view);
    }

    @Override
    public int getItemCount() {
        return kitchenModels.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public KitchenModel removeItem(int position) {
        final KitchenModel model = kitchenModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, KitchenModel model) {
        kitchenModels.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final KitchenModel model = kitchenModels.remove(fromPosition);
        kitchenModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<KitchenModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<KitchenModel> newModels) {
        for (int i = kitchenModels.size() - 1; i >= 0; i--) {
            final KitchenModel model = kitchenModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<KitchenModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final KitchenModel model = newModels.get(i);
            if (!kitchenModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<KitchenModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final KitchenModel model = newModels.get(toPosition);
            final int fromPosition = kitchenModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}