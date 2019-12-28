package com.andrukhiv.mynavigationdrawer.adapters;


import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.models.LibraryModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.transition.ViewPropertyTransition;

import java.util.List;


public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private List<LibraryModel> data;
    public LibraryAdapter(List<LibraryModel> data) {
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_library_card, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(data.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(GenericTransitionOptions.with(animationObject))
                .into(holder.image);
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    // Анімація завантаження картинки Glide
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);
        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(2500);
        fadeAnim.start();
    };
}
//public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
//
//    private RecyclerView parentRecycler;
//    private List<LibraryModel> data;
//    LibraryAdapter(List<LibraryModel> data) {
//        this.data = data;
//    }
//    private static final String TAG = "myLogs";
//
//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(Objects.requireNonNull(recyclerView));
//        parentRecycler = recyclerView;
//    }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View v = inflater.inflate(R.layout.item_library_card, parent, false);
//        return new ViewHolder(v);
//    }
//
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Glide.with(holder.itemView.getContext())
//                .load(data.get(position).getImage())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .transition(GenericTransitionOptions.with(animationObject))
//                .into(holder.image);
//    }
//
//
//    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private ImageView image;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//
//            image = itemView.findViewById(R.id.image);
//
//            itemView.findViewById(R.id.container).setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (v.getId() == R.id.container) {
//
//
//
//                Log.d(TAG, "Position " + data.get(getLayoutPosition()).getTitle());
//
//            }
//            parentRecycler.smoothScrollToPosition(getAdapterPosition());
//
//
//        }
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//
//    // Анімація завантаження картинки Glide
//    private ViewPropertyTransition.Animator animationObject = view -> {
//        view.setAlpha(0f);
//        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
//        fadeAnim.setDuration(2500);
//        fadeAnim.start();
//    };
//}