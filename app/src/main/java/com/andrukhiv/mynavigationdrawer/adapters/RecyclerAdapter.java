package com.andrukhiv.mynavigationdrawer.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.andrukhiv.mynavigationdrawer.utils.AnimationUtil;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.activity.DetailsActivity;
import com.andrukhiv.mynavigationdrawer.models.SpecificationsModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private final LayoutInflater mInflater;
    private List<SpecificationsModel> mItemList;
    private int Previusposition = 0;

    public RecyclerAdapter(Context context, List<SpecificationsModel> itemList) {
        mInflater = LayoutInflater.from(context);
        mItemList = new ArrayList<>(itemList);
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_academy);
        }
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.activity_card, parent, false);
        return new RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        final SpecificationsModel data = mItemList.get(position);
        CardView cardView = holder.cardView;

        TextView textName = cardView.findViewById(R.id.text_name);
        ImageView photoSmall = cardView.findViewById(R.id.photo_small);




        textName.setText(data.getName());
        Glide
                .with(cardView.getContext())
                .load(data.getPhotoSmall())
                .thumbnail(0.5f)// зменшив розмір попередного перегляду фото у 2 рази
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(GenericTransitionOptions.with(animationObject))
                .into(photoSmall);


        cardView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailsActivity.EXTRA_GRAPES_ID, mItemList.get(position));
            intent.putExtras(bundle);
            context.startActivity(intent);
           });


        // Анімація при завантаженні CardView
        Animation animation = AnimationUtils.loadAnimation(cardView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);

        if (position > Previusposition)
        {
            AnimationUtil.animate(holder,true);
        } else {
            AnimationUtil.animate(holder,false);
        }
        Previusposition = position;
    }


    @Override
    public int getItemCount() {
        return this.mItemList.size();
    }


    public void setItems(List<SpecificationsModel> datas) {
        mItemList = new ArrayList<>(datas);
        notifyDataSetChanged();
    }


    // Анімація завантаження картинки Glide
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);

        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(2500);
        fadeAnim.start();
    };



    public void animateTo(List<SpecificationsModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }

    private void applyAndAnimateRemovals(List<SpecificationsModel> newModels) {

        for (int i = mItemList.size() - 1; i >= 0; i--) {
            final SpecificationsModel model = mItemList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<SpecificationsModel> newModels) {

        for (int i = 0, count = newModels.size(); i < count; i++) {
            final SpecificationsModel model = newModels.get(i);
            if (!mItemList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<SpecificationsModel> newModels) {

        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final SpecificationsModel model = newModels.get(toPosition);
            final int fromPosition = mItemList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        final SpecificationsModel model = mItemList.remove(fromPosition);
        mItemList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addItem(int position, SpecificationsModel model) {
        mItemList.add(position, model);
        notifyItemInserted(position);
    }

    public SpecificationsModel removeItem(int position) {
        final SpecificationsModel model = mItemList.remove(position);
        notifyItemRemoved(position);
        return model;
    }
}
