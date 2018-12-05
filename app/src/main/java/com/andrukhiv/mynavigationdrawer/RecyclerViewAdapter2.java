package com.andrukhiv.mynavigationdrawer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.models.VarietiesModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.ViewPropertyTransition;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.DataObjectHolder> {

    private static String LOG_TAG = "RecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private Context context;

    protected ArrayList<VarietiesModel> mVarietiesModels;


    public RecyclerViewAdapter2(ArrayList<VarietiesModel> myDataset) {
        mVarietiesModels = new ArrayList<>(myDataset);
    }




    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView textName;
        ImageView photoSmall;

        public DataObjectHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            photoSmall = itemView.findViewById(R.id.photo_small);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(RecyclerViewAdapter2.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.textName.setText(mVarietiesModels.get(position).getName());
        //Picasso.get().load(mVarietiesModels.get(position).getPhotoSmall()).into(holder.photoSmall);
        Glide
                .with(context)
                .load(mVarietiesModels.get(position).getPhotoSmall())
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.placeholder)
//                        .fallback(R.drawable.ic_520016)
//                        .error(R.drawable.oops))
                .thumbnail(0.5f)// зменшив розмір попередного перегляду фото у 2 рази
                .transition(GenericTransitionOptions.with(animationObject))
                .into(holder.photoSmall);
    }


    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card, parent, false);

        context = parent.getContext();

        return new DataObjectHolder(view);
    }


    //4
    @Override
    public int getItemCount() {
        return mVarietiesModels.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }


    // Анімація завантаження картинки Glide
    public ViewPropertyTransition.Animator animationObject = new ViewPropertyTransition.Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha(0f);

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            fadeAnim.setDuration(2500);
            fadeAnim.start();
        }
    };


    public VarietiesModel removeItem(int position) {
        final VarietiesModel model = mVarietiesModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, VarietiesModel model) {
        mVarietiesModels.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final VarietiesModel model = mVarietiesModels.remove(fromPosition);
        mVarietiesModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<VarietiesModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<VarietiesModel> newModels) {
        for (int i = mVarietiesModels.size() - 1; i >= 0; i--) {
            final VarietiesModel model = mVarietiesModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<VarietiesModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final VarietiesModel model = newModels.get(i);
            if (!mVarietiesModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<VarietiesModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final VarietiesModel model = newModels.get(toPosition);
            final int fromPosition = mVarietiesModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


}