package com.andrukhiv.mynavigationdrawer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.models.LibraryModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;


import java.util.ArrayList;

public class LibraryRecyclerViewAdapter extends RecyclerView
        .Adapter<LibraryRecyclerViewAdapter
        .DataObjectHolder> {


    private static String LOG_TAG = "LibraryRecyclerViewAdapter";
    private ArrayList<LibraryModel> mDataset;
    private static MyClickListener myClickListener;
    private Context context;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView textTitle;
        TextView textAuthor;
        ImageView imageCover;

        DataObjectHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textAuthor = itemView.findViewById(R.id.text_author);
            imageCover = itemView.findViewById(R.id.image_cover);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    void setOnItemClickListener(MyClickListener myClickListener) {
        LibraryRecyclerViewAdapter.myClickListener = myClickListener;
    }


    LibraryRecyclerViewAdapter(ArrayList<LibraryModel> myDataset) {
        mDataset = myDataset;
    }


    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.library_card, parent, false);

        context = parent.getContext();

        return new DataObjectHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, int position) {
        holder.textTitle.setText(mDataset.get(position).getTitle());
        holder.textAuthor.setText(mDataset.get(position).getAuthor());
        Glide
                .with(context)
                .load(mDataset.get(position).getImage())

                .apply(new RequestOptions()
//                        .fitCenter()
                        .override(200, 150)
//                        .placeholder(R.drawable.placeholder)
//                        .fallback(R.drawable.ic_520016)
//                        .error(R.drawable.oops)
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(GenericTransitionOptions.with(animationObject))
                .into(holder.imageCover);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface MyClickListener {
        void onItemClick(int position, View v);
    }


    // Анімація завантаження картинки
    private ViewPropertyTransition.Animator animationObject = new ViewPropertyTransition.Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha(0f);

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            fadeAnim.setDuration(2500);
            fadeAnim.start();
        }
    };
}