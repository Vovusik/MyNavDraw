package com.andrukhiv.mynavigationdrawer;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.PreparationDialogModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class PreparationDialog extends BottomSheetDialog {


    private PreparationDialog(@NonNull Context context) {
        super(context);

        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.SheetDialog);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.preparation_dialog, null);

        ArrayList<PreparationDialogModel> preparationDialogModels = DbAdapter.getPreparationDialogTable();

        RecyclerView recyclerView = view.findViewById(R.id.preparation_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new SimpleAdapter(preparationDialogModels));

//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
//                DividerItemDecoration.VERTICAL));

        TextView textView = view.findViewById(R.id.preparation_dismiss);
        textView.setOnClickListener(v -> dialog.dismiss());

        dialog.setContentView(view);
        dialog.show();


    }


    public static void show(Context context) {
        new PreparationDialog(context);
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView shorter_dialog, longer_dialog;
        LinearLayoutCompat layout_content_view;

        ViewHolder(View itemView) {

            super(itemView);
            shorter_dialog = itemView.findViewById(R.id.shorter_dialog);
            longer_dialog = itemView.findViewById(R.id.longer_dialog);

            layout_content_view = itemView.findViewById(R.id.preparation_layout_content_view);
        }
    }


    private class SimpleAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<PreparationDialogModel> preparationDialogModelList;

        SimpleAdapter(List<PreparationDialogModel> laboratoryModelList) {

            this.preparationDialogModelList = laboratoryModelList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            @SuppressLint("InflateParams")
            View view = inflater.inflate(R.layout.preparation_dialog_row, null);
            return new ViewHolder(view);
        }


        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Context context = getContext();

            holder.shorter_dialog.setText(preparationDialogModelList.get(position).getShorter());
            holder.longer_dialog.setText(preparationDialogModelList.get(position).getLonger());
            holder.layout_content_view.setBackgroundColor(
                    position % 2 == 0 ?
                            ResourcesCompat.getColor(context.getResources(), R.color.rowBackground, null) :
                            ResourcesCompat.getColor(context.getResources(), R.color.windowBackground, null));


//            if (position % 2 == 1) {
//                holder.layout_content_view.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.windowBackground, null));
//            } else {
//                holder.layout_content_view.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.rowBackground, null));
//            }
        }


        @Override
        public int getItemCount() {
            if (preparationDialogModelList.size() > 0)
                return preparationDialogModelList.size();
            else
                return 0;
        }
    }
}
